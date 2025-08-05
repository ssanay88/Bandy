package com.yang.business.repository

import com.yang.business.model.UserSession
import kotlinx.coroutines.flow.Flow

interface UserSessionRepository {
    val userSession: Flow<UserSession>
    suspend fun updateCurrentUserInfo(updatedUserSession: UserSession)
    suspend fun clearUserSession()
}