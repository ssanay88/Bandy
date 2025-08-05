package com.yang.business.usecase.band

import com.yang.business.common.DataResourceResult
import com.yang.business.repository.BandRepository
import kotlinx.coroutines.flow.Flow

class DeleteBandUseCase(val bandRepository: BandRepository){
    suspend operator fun invoke(bandId: String): Flow<DataResourceResult<Unit>> {
        return bandRepository.deleteBand(bandId)
    }
}