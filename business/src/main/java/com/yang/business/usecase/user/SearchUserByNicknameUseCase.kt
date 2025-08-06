package com.yang.business.usecase.user

import com.yang.business.common.DataResourceResult
import com.yang.business.model.User
import com.yang.business.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class SearchUserByNicknameUseCase(val userRepository: UserRepository) {
    suspend operator fun invoke(userId: String) : Flow<DataResourceResult<List<User>>> {
        return userRepository.searchUserByNickname(userId)
    }
}