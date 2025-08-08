package suhyeok.yang.data.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Posting
import com.yang.business.repository.PostingHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import suhyeok.yang.data.datasource.PostingDataSource

class FirestorePostingHistoryRepositoryImpl(val targetDataSource: PostingDataSource): PostingHistoryRepository {
    override suspend fun readMyPosting(userId: String): Flow<DataResourceResult<List<Posting>>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.readMyPosting(userId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun readCommentedPosting(userId: String): Flow<DataResourceResult<List<Posting>>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.readCommentedPosting(userId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }
}