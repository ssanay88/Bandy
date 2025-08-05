package com.yang.business.usecase.chatroom

import com.yang.business.common.DataResourceResult
import com.yang.business.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow

class DeleteChatRoomUseCase(val chatRoomRepository: ChatRoomRepository) {
    suspend operator fun invoke(chatRoomId: String): Flow<DataResourceResult<Unit>> {
        return chatRoomRepository.deleteChatRoom(chatRoomId)
    }
}