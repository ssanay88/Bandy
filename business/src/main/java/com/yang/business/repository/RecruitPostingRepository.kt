package com.yang.business.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.RecruitPosting
import kotlinx.coroutines.flow.Flow

interface RecruitPostingRepository {
    suspend fun createRecruitPosting(newRecruitPosting: RecruitPosting): Flow<DataResourceResult<Unit>>
    suspend fun readRecruitPosting(postingId: String): Flow<DataResourceResult<RecruitPosting>>
    suspend fun readRecruitPostingList(): Flow<DataResourceResult<List<RecruitPosting>>>
    suspend fun updateRecruitPosting(updatedRecruitPosting: RecruitPosting): Flow<DataResourceResult<Unit>>
    suspend fun deleteRecruitPosting(postingId: String): Flow<DataResourceResult<Unit>>
}