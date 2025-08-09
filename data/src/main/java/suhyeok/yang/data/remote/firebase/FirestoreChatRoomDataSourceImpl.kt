package suhyeok.yang.data.remote.firebase

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yang.business.common.DataResourceResult
import com.yang.business.model.ChatRoom
import com.yang.business.model.Message
import com.yang.business.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import suhyeok.yang.data.datasource.ChatRoomDataSource
import suhyeok.yang.data.mapper.toBusinessChatRoomList
import suhyeok.yang.data.mapper.toBusinessMessage
import suhyeok.yang.data.mapper.toBusinessMessageList
import suhyeok.yang.data.mapper.toBusinessUserList
import suhyeok.yang.data.mapper.toFirestoreChatRoomDTO
import suhyeok.yang.data.mapper.toFirestoreMessageDTO
import suhyeok.yang.data.remote.dto.ChatRoomDTO
import suhyeok.yang.data.remote.dto.MessageDTO
import java.time.LocalDateTime

class FirestoreChatRoomDataSourceImpl : ChatRoomDataSource {

    companion object {
        const val CHAT_ROOM_COLLECTION = "chatRooms"
    }

    val db = Firebase.firestore

    override suspend fun createChatRoom(
        newChatRoom: ChatRoom
    ): DataResourceResult<Unit> = runCatching {
        withContext(Dispatchers.IO) {
            db.collection(CHAT_ROOM_COLLECTION)
                .add(newChatRoom.toFirestoreChatRoomDTO())
                .await()
            Log.d("tngur", "success -> ${newChatRoom.chatRoomId}")
            DataResourceResult.Success(Unit)
        }
    }.getOrElse {
        Log.d("tngur", "fail -> ${it.message}")
        DataResourceResult.Failure(it)
    }

//    override suspend fun createChatRoom(
//        newChatRoom: ChatRoom
//    ): DataResourceResult<Unit> = runCatching {
//        val newChatRoomDTO = newChatRoom.toFirestoreChatRoomDTO()
//
//        db.collection(CHAT_ROOM_COLLECTION)
//            .document(newChatRoom.chatRoomId)
//            .set(newChatRoomDTO)
//            .await()
//
//        DataResourceResult.Success(Unit)
//    }.getOrElse {
//        Log.d("tngur", "fail : ${it.message}")
//        DataResourceResult.Failure(it)
//    }

    override suspend fun readChatRoom(userId: String): DataResourceResult<List<ChatRoom>> =
        runCatching {
            val chatRoomSnapshot = db.collection(CHAT_ROOM_COLLECTION)
                .whereArrayContains("_participantIds", userId)
                .get()
                .await()

            val chatRoomDTOList = chatRoomSnapshot.toObjects(ChatRoomDTO::class.java)
            val chatRoomList = chatRoomDTOList.toBusinessChatRoomList()
                .sortedByDescending { it.lastMessageTimestamp ?: LocalDateTime.MIN }

            DataResourceResult.Success(chatRoomList)
        }.getOrElse {
            DataResourceResult.Failure(it)
        }

    override suspend fun updateChatRoom(
        updatedChatRoom: ChatRoom
    ): DataResourceResult<Unit> = runCatching {
        db.collection(CHAT_ROOM_COLLECTION)
            .whereEqualTo("_chatRoomId", updatedChatRoom.chatRoomId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.size() > 0) {
                    task.result.forEach {
                        db.collection(CHAT_ROOM_COLLECTION).document(it.id)
                            .update(updatedChatRoom.toFirestoreChatRoomDTO())
                    }
                }
            }.await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun deleteChatRoom(chatRoomId: String): DataResourceResult<Unit> =
        runCatching {
            db.collection(CHAT_ROOM_COLLECTION)
                .document(chatRoomId)
                .delete()
                .await()
            DataResourceResult.Success(Unit)
        }.getOrElse {
            DataResourceResult.Failure(it)
        }

    override suspend fun sendMessage(
        chatRoomId: String,
        message: Message
    ): DataResourceResult<Unit> = runCatching {
        val messageDTO = MessageDTO(
            _messageId = message.messageId,
            _senderId = message.senderId,
            _chatRoomId = message.chatRoomId,
            _content = message.content,
            _timestamp = message.timestamp.toString()
        )

        val chatRoomDocRef = db.collection(CHAT_ROOM_COLLECTION).document(chatRoomId)
        // 기존 메시지 리스트 불러오기
        val chatRoomSnapshot = chatRoomDocRef.get().await()
        val existingChatRoomDTO = chatRoomSnapshot.toObject(ChatRoomDTO::class.java)
        val updatedMessages = existingChatRoomDTO?._messages?.toMutableList() ?: mutableListOf()

        updatedMessages.add(messageDTO)

        // chatRoom 문서 자체 업데이트
        chatRoomDocRef.update(
            mapOf(
                "_messages" to updatedMessages,
                "_lastMessage" to message.content,
                "_lastMessageTimestamp" to message.timestamp.toString()
            )
        ).await()

        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun observeMessages(chatRoomId: String): DataResourceResult<List<Message>> = runCatching {
        val messagesSnapshot = db.collection(CHAT_ROOM_COLLECTION)
            .document(chatRoomId)
            .collection("_messages")

        messagesSnapshot.addSnapshotListener { snapshot, error ->
            if (error != null) {
                DataResourceResult.Failure(Exception("Failed to retrieve messages"))
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val messages = snapshot.toObjects(MessageDTO::class.java)
                DataResourceResult.Success(messages.toBusinessMessageList())
            }
        }
        DataResourceResult.Uninitialized
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun markMessagesAsRead(
        chatRoomId: String,
        userId: String
    ): DataResourceResult<Unit> = runCatching {
        val messagesSnapshot = db.collection(CHAT_ROOM_COLLECTION)
            .document(chatRoomId)
            .collection("messages")
            .whereArrayContains("_readUserIds", userId)
            .get()
            .await()

        val batch = db.batch()
        messagesSnapshot.documents.forEach { doc ->
            val docRef = doc.reference
            batch.update(docRef, "_readUserIds", FieldValue.arrayUnion(userId))
        }
        batch.commit().await()

        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun getUnreadMessageCount(chatRoomId: String, userId: String): Int = runCatching {
        val snapshot = db.collection(CHAT_ROOM_COLLECTION)
            .document(chatRoomId)
            .get()
            .await()

        val dto = snapshot.toObject(ChatRoomDTO::class.java)
        dto?._messages?.count { userId in it._unreadUserIds } ?: 0
    }.getOrElse {
        0
    }


    override suspend fun getChatParticipants(chatRoomId: String): List<User> = runCatching {
        val chatRoomDoc = db.collection(CHAT_ROOM_COLLECTION)
            .document(chatRoomId)
            .get()
            .await()

        val dto = chatRoomDoc.toObject(ChatRoomDTO::class.java)
        (dto?._participants ?: emptyList()).toBusinessUserList()
    }.getOrElse {
        emptyList()
    }


    override suspend fun deleteMessage(
        chatRoomId: String,
        messageId: String
    ): DataResourceResult<Unit> = runCatching {
        db.collection(CHAT_ROOM_COLLECTION)
            .document(chatRoomId)
            .collection("messages")
            .document(messageId)
            .delete()
            .await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

}