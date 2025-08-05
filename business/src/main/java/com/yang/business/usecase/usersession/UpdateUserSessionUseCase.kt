package com.yang.business.usecase.usersession

import com.yang.business.model.UserSession
import com.yang.business.repository.UserSessionRepository

class UpdateUserSessionUseCase(val userSessionRepository: UserSessionRepository) {
    suspend operator fun invoke(updatedUserSession: UserSession) {
        userSessionRepository.updateCurrentUserInfo(updatedUserSession)
    }
}