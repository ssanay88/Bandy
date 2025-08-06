package suhyeok.yang.data.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.User
import com.yang.business.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import suhyeok.yang.data.datasource.UserDataSource

class FirestoreUserRepositoryImpl(val targetDataSource: UserDataSource): UserRepository {
    override suspend fun readUser(userId: String): Flow<DataResourceResult<User>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.readUser(userId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun createUser(newUser: User): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.createUser(newUser))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun updateUser(updatedUser: User): Flow<DataResourceResult<Unit>>  = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.updateUser(updatedUser))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun deleteUser(userId: String): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.deleteUser(userId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun searchUserByNickname(nickname: String): Flow<DataResourceResult<List<User>>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.searchUserByNickname(nickname))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }
}