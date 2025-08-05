package suhyeok.yang.data.datasource

import com.yang.business.common.DataResourceResult
import com.yang.business.model.ChatRoom
import com.yang.business.model.Message
import com.yang.business.model.User
import kotlinx.coroutines.flow.Flow

interface ChatRoomDataSource {
    suspend fun createChatRoom(newChatRoom: ChatRoom): DataResourceResult<Unit>
    suspend fun readChatRoom(userId: String): DataResourceResult<List<ChatRoom>>
    suspend fun updateChatRoom(updatedChatRoom: ChatRoom): DataResourceResult<Unit>
    suspend fun deleteChatRoom(chatRoomId: String): DataResourceResult<Unit>

    suspend fun sendMessage(chatRoomId: String, message: Message): DataResourceResult<Unit>
    suspend fun observeMessages(chatRoomId: String): List<Message>
    suspend fun markMessagesAsRead(chatRoomId: String, userId: String): DataResourceResult<Unit>
    suspend fun getUnreadMessageCount(chatRoomId: String, userId: String): Int
    suspend fun getChatParticipants(chatRoomId: String): List<User>
    suspend fun deleteMessage(chatRoomId: String, messageId: String): DataResourceResult<Unit>

}