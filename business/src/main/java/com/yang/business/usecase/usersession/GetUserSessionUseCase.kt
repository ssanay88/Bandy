package com.yang.business.usecase.usersession

import com.yang.business.model.UserSession
import com.yang.business.repository.UserSessionRepository
import kotlinx.coroutines.flow.Flow

class GetUserSessionUseCase(val userSessionRepository: UserSessionRepository) {
    suspend operator fun invoke(): Flow<UserSession> {
        return userSessionRepository.userSession
    }
}