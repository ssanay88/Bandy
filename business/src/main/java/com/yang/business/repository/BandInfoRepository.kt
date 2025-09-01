package com.yang.business.repository

import com.yang.business.common.DataResourceResult
import kotlinx.coroutines.flow.Flow

interface BandInfoRepository {
    suspend fun addMember(bandId: String, userId: String): Flow<DataResourceResult<Unit>>
    suspend fun removeMember(bandId: String, removedUserId: String): Flow<DataResourceResult<Unit>>
    suspend fun handOverLeader(bandId: String, prevLeaderId: String, newLeaderId: String): Flow<DataResourceResult<Unit>>
}