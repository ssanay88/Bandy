package com.yang.business.usecase.user

import com.yang.business.common.DataResourceResult
import com.yang.business.model.User
import com.yang.business.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class CreateUserUseCase(val userRepository: UserRepository) {
    suspend operator fun invoke(newUser: User): Flow<DataResourceResult<Unit>> {
        return userRepository.createUser(newUser)
    }
}