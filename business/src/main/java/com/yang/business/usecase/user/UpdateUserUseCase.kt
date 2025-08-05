package com.yang.business.usecase.user

import com.yang.business.common.DataResourceResult
import com.yang.business.model.User
import com.yang.business.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UpdateUserUseCase(val userRepository: UserRepository) {
    suspend operator fun invoke(updatedUser: User): Flow<DataResourceResult<Unit>> {
        return userRepository.updateUser(updatedUser)
    }
}