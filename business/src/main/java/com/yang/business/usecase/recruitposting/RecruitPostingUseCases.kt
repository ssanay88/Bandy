package com.yang.business.usecase.recruitposting

data class RecruitPostingUseCases(
    val createRecruitPosting: CreateRecruitPostingUseCase,
    val readRecruitPosting: ReadRecruitPostingUseCase,
    val readRecruitPostingList: ReadRecruitPostingListUseCase,
    val updateRecruitPosting: UpdateRecruitPostingUseCase,
    val deleteRecruitPosting: DeleteRecruitPostingUseCase
)
