package com.yang.business.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun loginWithNaver(): Flow<DataResourceResult<LoginResponse>>
}