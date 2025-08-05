package com.yang.business.usecase.usersession

import com.yang.business.repository.UserSessionRepository

class ClearUserSessionUseCase(val userSessionRepository: UserSessionRepository) {
    suspend operator fun invoke() {
        userSessionRepository.clearUserSession()
    }
}