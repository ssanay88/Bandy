package com.yang.business.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun readUser(userId: String): Flow<DataResourceResult<User>>
    suspend fun createUser(newUser: User): Flow<DataResourceResult<Unit>>
    suspend fun updateUser(updatedUser: User): Flow<DataResourceResult<Unit>>
    suspend fun deleteUser(userId: String): Flow<DataResourceResult<Unit>>
}