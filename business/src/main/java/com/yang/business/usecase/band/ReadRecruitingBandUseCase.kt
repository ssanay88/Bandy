package com.yang.business.usecase.band

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Band
import com.yang.business.repository.BandRepository
import kotlinx.coroutines.flow.Flow

class ReadRecruitingBandUseCase(val bandRepository: BandRepository) {
    suspend operator fun invoke(): Flow<DataResourceResult<List<Band>>> {
        return bandRepository.readRecruitingBand()
    }
}