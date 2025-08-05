package suhyeok.yang.data.datasource

import com.yang.business.model.UserSession
import kotlinx.coroutines.flow.Flow

interface UserSessionDataSource {
    val userSession: Flow<UserSession>
    suspend fun updateCurrentUserInfo(updatedUserSession: UserSession)
    suspend fun clearUserSession()
}