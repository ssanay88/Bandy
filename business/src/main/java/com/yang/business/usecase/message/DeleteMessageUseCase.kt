package com.yang.business.usecase.message

import com.yang.business.common.DataResourceResult
import com.yang.business.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow

class DeleteMessageUseCase(private val chatRoomRepository: ChatRoomRepository) {
    suspend operator fun invoke(chatRoomId: String, messageId: String): Flow<DataResourceResult<Unit>> {
        return chatRoomRepository.deleteMessage(chatRoomId, messageId)
    }
}