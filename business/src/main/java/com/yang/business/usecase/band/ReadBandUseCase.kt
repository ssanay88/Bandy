package com.yang.business.usecase.band

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Band
import com.yang.business.repository.BandRepository
import kotlinx.coroutines.flow.Flow

class ReadBandUseCase(val bandRepository: BandRepository) {
    suspend operator fun invoke(bandId: String): Flow<DataResourceResult<Band>> {
        return bandRepository.readBand(bandId)
    }
}