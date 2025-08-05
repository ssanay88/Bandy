package com.yang.business.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Posting
import kotlinx.coroutines.flow.Flow

interface PostingRepository {
    suspend fun createPosting(newPosting: Posting): Flow<DataResourceResult<Unit>>
    suspend fun readPosting(): Flow<DataResourceResult<List<Posting>>>
    suspend fun readPostingDetail(postingId: String): Flow<DataResourceResult<Posting>>
    suspend fun updatePosting(updatedPosting: Posting): Flow<DataResourceResult<Unit>>
    suspend fun deletePosting(postingId: String): Flow<DataResourceResult<Unit>>
}