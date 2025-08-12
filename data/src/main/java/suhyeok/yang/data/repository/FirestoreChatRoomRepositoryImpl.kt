package suhyeok.yang.data.repository

import android.util.Log
import com.yang.business.common.DataResourceResult
import com.yang.business.model.ChatRoom
import com.yang.business.model.Message
import com.yang.business.model.User
import com.yang.business.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import suhyeok.yang.data.datasource.ChatRoomDataSource

class FirestoreChatRoomRepositoryImpl(val targetResource: ChatRoomDataSource): ChatRoomRepository {
    override suspend fun createChatRoom(newChatRoom: ChatRoom): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetResource.createChatRoom(newChatRoom))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun readChatRoom(userId: String): Flow<DataResourceResult<List<ChatRoom>>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetResource.readChatRoom(userId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun updateChatRoom(updatedChatRoom: ChatRoom): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetResource.updateChatRoom(updatedChatRoom))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun deleteChatRoom(chatRoomId: String): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetResource.deleteChatRoom(chatRoomId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun sendMessage(
        chatRoomId: String,
        message: Message
    ): Flow<DataResourceResult<Unit>> = flow {
        emit(targetResource.sendMessage(chatRoomId, message))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun observeMessages(chatRoomId: String): Flow<DataResourceResult<List<Message>>> = targetResource.observeMessages(chatRoomId)

    override suspend fun markMessagesAsRead(chatRoomId: String, userId: String): Flow<DataResourceResult<Unit>> = flow {
        emit(targetResource.markMessagesAsRead(chatRoomId, userId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun getUnreadMessageCount(chatRoomId: String, userId: String): Flow<Int> = flow {
        emit(targetResource.getUnreadMessageCount(chatRoomId, userId))
    }

    override suspend fun getChatParticipants(chatRoomId: String): Flow<List<User>> = flow {
        emit(targetResource.getChatParticipants(chatRoomId))
    }

    override suspend fun deleteMessage(
        chatRoomId: String,
        messageId: String
    ): Flow<DataResourceResult<Unit>> = flow {
        emit(targetResource.deleteMessage(chatRoomId, messageId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }
}