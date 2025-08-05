package suhyeok.yang.data.datasource

import com.yang.business.common.DataResourceResult
import com.yang.business.model.RecruitPosting

interface RecruitPostingDataSource {
    suspend fun createRecruitPosting(newRecruitPosting: RecruitPosting): DataResourceResult<Unit>
    suspend fun readRecruitPosting(postingId: String): DataResourceResult<RecruitPosting>
    suspend fun readRecruitPostingList(): DataResourceResult<List<RecruitPosting>>
    suspend fun updateRecruitPosting(updatedRecruitPosting: RecruitPosting): DataResourceResult<Unit>
    suspend fun deleteRecruitPosting(postingId: String): DataResourceResult<Unit>
}