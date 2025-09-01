package com.yang.business.usecase.manageband

import com.yang.business.repository.BandInfoRepository

class AddMemberUseCase(
    val bandInfoRepository: BandInfoRepository
) {
    suspend operator fun invoke(bandId: String, userId: String) = bandInfoRepository.addMember(bandId, userId)
}