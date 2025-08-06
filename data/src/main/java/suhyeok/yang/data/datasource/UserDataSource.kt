package suhyeok.yang.data.datasource

import com.yang.business.common.DataResourceResult
import com.yang.business.model.User

interface UserDataSource {
    suspend fun readUser(userId: String): DataResourceResult<User>
    suspend fun createUser(newUser: User): DataResourceResult<Unit>
    suspend fun updateUser(updatedUser: User): DataResourceResult<Unit>
    suspend fun deleteUser(userId: String): DataResourceResult<Unit>
    suspend fun searchUserByNickname(nickname: String): DataResourceResult<List<User>>
}