package com.yang.business.usecase.message

import com.yang.business.model.User
import com.yang.business.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow

class GetChatParticipantsUseCase(private val chatRoomRepository: ChatRoomRepository) {
    suspend operator fun invoke(chatRoomId: String): Flow<List<User>> {
        return chatRoomRepository.getChatParticipants(chatRoomId)
    }
}
