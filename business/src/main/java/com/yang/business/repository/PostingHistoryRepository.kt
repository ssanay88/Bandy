package com.yang.business.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Posting
import kotlinx.coroutines.flow.Flow

interface PostingHistoryRepository {
    suspend fun readMyPosting(userId: String): Flow<DataResourceResult<List<Posting>>>
    suspend fun readCommentedPosting(userId: String): Flow<DataResourceResult<List<Posting>>>
}