package com.yang.business.usecase.message

import com.yang.business.common.DataResourceResult
import com.yang.business.model.User
import com.yang.business.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class GetUserInfoUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): Flow<DataResourceResult<User>> {
        return userRepository.readUser(userId)
    }
}