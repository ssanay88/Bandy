package com.yang.business.usecase.message

data class MessageUseCases(
    val sendMessageUseCase: SendMessageUseCase,
    val observeMessageUseCase: ObserveMessageUseCase,
    val markMessageAsReadUseCase: MarkMessageAsReadUseCase,
    val getUnreadMessageCountUserCase: GetUnreadMessageCountUseCase,
    val getChatParticipantsUseCase: GetChatParticipantsUseCase,
    val getUserInfoUseCase: GetUserInfoUseCase,
    val deleteMessageUseCase: DeleteMessageUseCase,
)