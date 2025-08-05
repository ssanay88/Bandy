package suhyeok.yang.data.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.RecruitPosting
import com.yang.business.repository.RecruitPostingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import suhyeok.yang.data.datasource.RecruitPostingDataSource

class FirestoreRecruitPostingRepositoryImpl(val targetDataSource: RecruitPostingDataSource): RecruitPostingRepository {
    override suspend fun createRecruitPosting(newRecruitPosting: RecruitPosting): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.createRecruitPosting(newRecruitPosting))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun readRecruitPosting(postingId: String): Flow<DataResourceResult<RecruitPosting>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.readRecruitPosting(postingId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun readRecruitPostingList(): Flow<DataResourceResult<List<RecruitPosting>>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.readRecruitPostingList())
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun updateRecruitPosting(updatedRecruitPosting: RecruitPosting): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.updateRecruitPosting(updatedRecruitPosting))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun deleteRecruitPosting(postingId: String): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.deleteRecruitPosting(postingId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }
}