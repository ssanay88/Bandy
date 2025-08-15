package com.yang.business.usecase.auth

import com.yang.business.common.DataResourceResult
import com.yang.business.model.LoginResponse
import com.yang.business.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class LoginWithNaverUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Flow<DataResourceResult<LoginResponse>> {
        return authRepository.loginWithNaver()
    }
}