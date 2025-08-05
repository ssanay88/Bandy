package com.yang.business.usecase.chatroom

import com.yang.business.common.DataResourceResult
import com.yang.business.model.ChatRoom
import com.yang.business.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow

class UpdateChatRoomUseCase(val chatRoomRepository: ChatRoomRepository) {
    suspend operator fun invoke(updatedChatRoom: ChatRoom): Flow<DataResourceResult<Unit>> {
        return chatRoomRepository.updateChatRoom(updatedChatRoom)
    }
}