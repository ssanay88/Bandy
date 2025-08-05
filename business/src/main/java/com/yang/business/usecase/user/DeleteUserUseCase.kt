package com.yang.business.usecase.user

import com.yang.business.common.DataResourceResult
import com.yang.business.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class DeleteUserUseCase(val userRepository: UserRepository) {
    suspend operator fun invoke(userId: String): Flow<DataResourceResult<Unit>> {
        return userRepository.deleteUser(userId)
    }
}