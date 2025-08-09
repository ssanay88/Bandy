package com.yang.business.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.ChatRoom
import com.yang.business.model.Message
import com.yang.business.model.User
import kotlinx.coroutines.flow.Flow

interface ChatRoomRepository {
    suspend fun createChatRoom(newChatRoom: ChatRoom): Flow<DataResourceResult<Unit>>
    suspend fun readChatRoom(userId: String): Flow<DataResourceResult<List<ChatRoom>>>
    suspend fun updateChatRoom(updatedChatRoom: ChatRoom): Flow<DataResourceResult<Unit>>
    suspend fun deleteChatRoom(chatRoomId: String): Flow<DataResourceResult<Unit>>

    // Message-related methods
    suspend fun sendMessage(chatRoomId: String, message: Message): Flow<DataResourceResult<Unit>>
    fun observeMessages(chatRoomId: String): Flow<DataResourceResult<List<Message>>>
    suspend fun markMessagesAsRead(chatRoomId: String, userId: String): Flow<DataResourceResult<Unit>>
    suspend fun getUnreadMessageCount(chatRoomId: String, userId: String): Flow<Int>
    suspend fun getChatParticipants(chatRoomId: String): Flow<List<User>>
    suspend fun deleteMessage(chatRoomId: String, messageId: String): Flow<DataResourceResult<Unit>>
}