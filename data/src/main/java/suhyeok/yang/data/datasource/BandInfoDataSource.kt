package suhyeok.yang.data.datasource

import com.yang.business.common.DataResourceResult

interface BandInfoDataSource {
    suspend fun addMember(bandId: String, userId: String): DataResourceResult<Unit>
    suspend fun removeMember(bandId: String, removedUserId: String): DataResourceResult<Unit>
    suspend fun changeLeader(bandId: String, prevLeaderId: String, newLeaderId: String): DataResourceResult<Unit>
}