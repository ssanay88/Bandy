package com.yang.business.usecase.message

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Message
import com.yang.business.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow

class SendMessageUseCase(private val chatRoomRepository: ChatRoomRepository) {
    suspend operator fun invoke(chatRoomId: String, message: Message): Flow<DataResourceResult<Unit>> {
        return chatRoomRepository.sendMessage(chatRoomId, message)
    }
}