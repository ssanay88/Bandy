package com.yang.business.usecase.chatroom

data class ChatRoomUseCases(
    val createChatRoomUseCase: CreateChatRoomUseCase,
    val readChatRoomUseCase: ReadChatRoomUseCase,
    val updateChatRoomUseCase: UpdateChatRoomUseCase,
    val deleteChatRoomUseCase: DeleteChatRoomUseCase
)