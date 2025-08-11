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
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
        const val MESSAGE_COLLECTION = "messages"
    }

    val db = Firebase.firestore

    override suspend fun createChatRoom(
        newChatRoom: ChatRoom
    ): DataResourceResult<Unit> = runCatching {
        val newChatRoomDTO = newChatRoom.toFirestoreChatRoomDTO()

        // 채팅방 문서와 채팅 내용 서브 컬렉션의 문서를 동시에 생성
        // 두 작업 중 하나라도 실패하면 모두 롤백
        val batch = db.batch()

        // 채팅방 컬렉션에 신규 채팅방 정보 문서 추가
        val chatRoomRef = db.collection(CHAT_ROOM_COLLECTION).document(newChatRoom.chatRoomId)
        batch.set(chatRoomRef, newChatRoomDTO)

        // 채팅방 문서에 채팅 내용에 관한 컬렉션 추가
        newChatRoom.messages.forEach { message ->
            val messageDTO = message.toFirestoreMessageDTO()
            val messageRef = chatRoomRef.collection(MESSAGE_COLLECTION).document(message.messageId)
            batch.set(messageRef, messageDTO)
        }

        batch.commit().await()

        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

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
        val batch = db.batch()

        val messageDTO = MessageDTO(
            _messageId = message.messageId,
            _senderId = message.senderId,
            _chatRoomId = message.chatRoomId,
            _content = message.content,
            _timestamp = message.timestamp.toString(),
            _readUserIds = message.readUserIds
        )

        val messagesRef = db.collection(CHAT_ROOM_COLLECTION).document(chatRoomId).collection(MESSAGE_COLLECTION).document(message.messageId)
        batch.set(messagesRef, messageDTO)

        val chatRoomRef = db.collection(CHAT_ROOM_COLLECTION).document(chatRoomId)
        batch.update(chatRoomRef, "_lastMessage", message.content)
        batch.update(chatRoomRef, "_lastMessageTimestamp", message.timestamp.toString())

        batch.commit().await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun observeMessages(chatRoomId: String): Flow<DataResourceResult<List<Message>>> =
        callbackFlow {
            trySend(DataResourceResult.Loading)

            val registration = db.collection(CHAT_ROOM_COLLECTION)
                .document(chatRoomId)
                .collection(MESSAGE_COLLECTION)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        trySend(DataResourceResult.Failure(Exception("Failed to retrieve messages")))
                        close(error)
                    }

                    if (snapshot != null && !snapshot.isEmpty) {
                        val messages = snapshot.toObjects(MessageDTO::class.java)
                        trySend(
                            DataResourceResult.Success(
                                messages.toBusinessMessageList()
                                    .sortedBy { it.timestamp })
                        )
                    }
                }

            awaitClose {
                registration.remove()
            }
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

    override suspend fun getUnreadMessageCount(chatRoomId: String, userId: String): Int =
        runCatching {
            val snapshot = db.collection(CHAT_ROOM_COLLECTION)
                .document(chatRoomId)
                .get()
                .await()

            val dto = snapshot.toObject(ChatRoomDTO::class.java)
            val totalParticipantsCount = dto?._participants?.size ?: 1
            val readParticipantsCount = dto?._participants?.count { it._userId == userId } ?: 1
            totalParticipantsCount - readParticipantsCount
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