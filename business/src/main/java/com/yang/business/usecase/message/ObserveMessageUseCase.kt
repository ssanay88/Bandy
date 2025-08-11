package com.yang.business.usecase.message

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Message
import com.yang.business.repository.ChatRoomRepository
import kotlinx.coroutines.flow.Flow

class ObserveMessageUseCase(private val chatRoomRepository: ChatRoomRepository) {
    suspend operator fun invoke(chatRoomId: String): Flow<DataResourceResult<List<Message>>> {
        return chatRoomRepository.observeMessages(chatRoomId)
    }
}
