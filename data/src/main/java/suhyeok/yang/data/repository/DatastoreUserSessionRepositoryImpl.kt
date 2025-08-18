package suhyeok.yang.data.repository

import com.yang.business.model.UserSession
import com.yang.business.repository.UserSessionRepository
import kotlinx.coroutines.flow.Flow
import suhyeok.yang.data.datasource.UserSessionDataSource
import javax.inject.Inject

class DatastoreUserSessionRepositoryImpl @Inject constructor(
    private val userSessionDataSource: UserSessionDataSource
): UserSessionRepository {

    override val userSession: Flow<UserSession>
        get() = userSessionDataSource.userSession

    override suspend fun updateCurrentUserInfo(updatedUserSession: UserSession) {
        userSessionDataSource.updateCurrentUserInfo(updatedUserSession)
    }

    override suspend fun clearUserSession() {
        userSessionDataSource.clearUserSession()
    }
}