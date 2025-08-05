package com.yang.business.usecase.message

import com.yang.business.common.DataResourceResult
import com.yang.business.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow

class MarkMessageAsReadUseCase(private val chatRoomRepository: ChatRoomRepository) {
    suspend operator fun invoke(chatRoomId: String, userId: String): Flow<DataResourceResult<Unit>> {
        return chatRoomRepository.markMessagesAsRead(chatRoomId, userId)
    }
}
