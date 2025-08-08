package com.yang.business.usecase.postinghistory

data class PostingHistoryUseCases(
    val readMyPosting: ReadMyPostingUseCase,
    val readCommentedPosting: ReadCommentedPostingUseCase
)