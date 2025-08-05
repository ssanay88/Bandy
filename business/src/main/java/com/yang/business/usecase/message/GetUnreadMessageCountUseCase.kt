package com.yang.business.usecase.message

import com.yang.business.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow

class GetUnreadMessageCountUseCase(private val chatRoomRepository: ChatRoomRepository) {
    suspend operator fun invoke(chatRoomId: String, userId: String): Flow<Int> {
        return chatRoomRepository.getUnreadMessageCount(chatRoomId, userId)
    }
}
