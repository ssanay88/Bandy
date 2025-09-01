package com.yang.business.usecase.manageband

import com.yang.business.repository.BandInfoRepository

class RemoveMemberUseCase(
    private val bandInfoRepository: BandInfoRepository
) {
    suspend operator fun invoke(bandId: String, removedUserId: String) = bandInfoRepository.removeMember(bandId, removedUserId)
}