package com.yang.business.usecase.band

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Band
import com.yang.business.repository.BandRepository
import kotlinx.coroutines.flow.Flow

class CreateBandUseCase(val bandRepository: BandRepository) {
    suspend operator fun invoke(newBand: Band): Flow<DataResourceResult<Unit>> {
        return bandRepository.createBand(newBand)
    }
}