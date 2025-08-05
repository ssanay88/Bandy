package com.yang.business.usecase.chatroom

import com.yang.business.common.DataResourceResult
import com.yang.business.model.ChatRoom
import com.yang.business.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow

class ReadChatRoomUseCase(val chatRoomRepository: ChatRoomRepository) {
    suspend operator fun invoke(userId: String): Flow<DataResourceResult<List<ChatRoom>>> {
        return chatRoomRepository.readChatRoom(userId)
    }
}