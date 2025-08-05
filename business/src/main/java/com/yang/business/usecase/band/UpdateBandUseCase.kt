package com.yang.business.usecase.band

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Band
import com.yang.business.repository.BandRepository
import kotlinx.coroutines.flow.Flow

class UpdateBandUseCase(val bandRepository: BandRepository) {
    suspend operator fun invoke(updatedBand: Band): Flow<DataResourceResult<Unit>> {
        return bandRepository.updateBand(updatedBand)
    }
}