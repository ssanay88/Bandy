package com.yang.business.usecase.posting

data class PostingUseCases(
    val createPosting: CreatePostingUseCase,
    val readPosting: ReadPostingUseCase,
    val readPostingDetail: ReadPostingDetailUseCase,
    val updatePosting: UpdatePostingUseCase,
    val deletePosting: DeletePostingUseCase
)