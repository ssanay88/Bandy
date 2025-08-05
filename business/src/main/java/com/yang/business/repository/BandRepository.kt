package com.yang.business.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Band
import kotlinx.coroutines.flow.Flow

interface BandRepository {
    suspend fun createBand(newBand: Band): Flow<DataResourceResult<Unit>>
    suspend fun readBand(bandId: String): Flow<DataResourceResult<Band>>
    suspend fun readPopularBand(): Flow<DataResourceResult<List<Band>>>
    suspend fun readRecruitingBand(): Flow<DataResourceResult<List<Band>>>
    suspend fun readBandList(): Flow<DataResourceResult<List<Band>>>
    suspend fun updateBand(updatedBand: Band): Flow<DataResourceResult<Unit>>
    suspend fun deleteBand(bandId: String): Flow<DataResourceResult<Unit>>
}