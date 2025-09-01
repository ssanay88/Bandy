package com.yang.business.usecase.manageband

data class ManageBandUseCases(
    val addMember: AddMemberUseCase,
    val removeMember: RemoveMemberUseCase,
    val handOverLeader: ChangeLeaderUseCase
)