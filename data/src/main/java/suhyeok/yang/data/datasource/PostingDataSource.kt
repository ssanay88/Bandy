package suhyeok.yang.data.datasource

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Posting

interface PostingDataSource {
    suspend fun createPosting(newPosting: Posting): DataResourceResult<Unit>
    suspend fun readPosting(): DataResourceResult<List<Posting>>
    suspend fun readPostingDetail(postingId: String): DataResourceResult<Posting>
    suspend fun updatePosting(updatedPosting: Posting): DataResourceResult<Unit>
    suspend fun deletePosting(postingId: String): DataResourceResult<Unit>
}