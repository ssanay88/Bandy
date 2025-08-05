package suhyeok.yang.data.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Posting
import com.yang.business.repository.PostingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import suhyeok.yang.data.datasource.PostingDataSource

class FirestorePostingRepositoryImpl(val targetDataSource: PostingDataSource) : PostingRepository {
    override suspend fun createPosting(newPosting: Posting): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.createPosting(newPosting))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun readPosting(): Flow<DataResourceResult<List<Posting>>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.readPosting())
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun readPostingDetail(postingId: String): Flow<DataResourceResult<Posting>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.readPostingDetail(postingId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun updatePosting(updatedPosting: Posting): Flow<DataResourceResult<Unit>> =
        flow {
            emit(DataResourceResult.Loading)
            emit(targetDataSource.updatePosting(updatedPosting))
        }.catch {
            emit(DataResourceResult.Failure(it))
        }

    override suspend fun deletePosting(postingId: String): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.deletePosting(postingId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }
}