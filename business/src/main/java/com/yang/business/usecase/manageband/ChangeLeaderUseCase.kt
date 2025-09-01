package com.yang.business.usecase.manageband

import com.yang.business.repository.BandInfoRepository

class ChangeLeaderUseCase(private val bandInfoRepository: BandInfoRepository) {
    suspend operator fun invoke(bandId: String, prevLeaderId: String, newLeaderId: String) =
        bandInfoRepository.handOverLeader(bandId, prevLeaderId, newLeaderId)
}