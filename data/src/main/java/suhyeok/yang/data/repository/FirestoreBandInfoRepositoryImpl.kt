package suhyeok.yang.data.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.repository.BandInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import suhyeok.yang.data.datasource.BandInfoDataSource
import javax.inject.Inject

class FirestoreBandInfoRepositoryImpl @Inject constructor(
    val bandInfoDataSource: BandInfoDataSource
) : BandInfoRepository {
    override suspend fun addMember(
        bandId: String,
        userId: String,
    ): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(bandInfoDataSource.addMember(bandId, userId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun removeMember(
        bandId: String,
        removedUserId: String,
    ): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(bandInfoDataSource.removeMember(bandId, removedUserId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun handOverLeader(
        bandId: String,
        prevLeaderId: String,
        newLeaderId: String,
    ): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(bandInfoDataSource.changeLeader(bandId, prevLeaderId, newLeaderId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }
}