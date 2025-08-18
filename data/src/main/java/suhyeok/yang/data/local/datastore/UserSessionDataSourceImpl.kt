package suhyeok.yang.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.yang.business.model.UserSession
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import suhyeok.yang.data.datasource.UserSessionDataSource
import suhyeok.yang.data.datastore.UserPreferencesKeys
import suhyeok.yang.data.local.dto.UserSessionEntity
import suhyeok.yang.data.mapper.toBusinessUserSession
import suhyeok.yang.data.mapper.toDataStoreUserSessionEntity
import javax.inject.Inject
import javax.inject.Singleton

class UserSessionDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserSessionDataSource {

    companion object {
        const val USER_SESSION_DATASTORE = "user_session_datastore"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SESSION_DATASTORE)

    override val userSession: Flow<UserSession> = context.dataStore.data.map { pref ->
        val isLoggedIn = pref[UserPreferencesKeys.IS_LOGGED_IN] ?: false
        val userId = pref[UserPreferencesKeys.USER_ID] ?: ""
        val userName = pref[UserPreferencesKeys.USER_NAME] ?: ""
        val userNickname = pref[UserPreferencesKeys.USER_NICKNAME] ?: ""
        val userProfileImage = pref[UserPreferencesKeys.USER_PROFILE_IMAGE] ?: ""
        val userDescription = pref[UserPreferencesKeys.USER_DESCRIPTION] ?: ""
        val userInstrument = pref[UserPreferencesKeys.USER_INSTRUMENT] ?: ""
        val userRegion = pref[UserPreferencesKeys.USER_REGION] ?: ""
        val isLeader = pref[UserPreferencesKeys.IS_LEADER] ?: false
        val isBand = pref[UserPreferencesKeys.IS_BAND] ?: false
        val bandId = pref[UserPreferencesKeys.BAND_ID] ?: ""

        val userSessionEntity = UserSessionEntity(
            _isLogged = isLoggedIn,
            _userId = userId,
            _userName = userName,
            _userNickname = userNickname,
            _userProfileImage = userProfileImage,
            _userDescription = userDescription,
            _userInstrument = userInstrument,
            _userRegion = userRegion,
            _isLeader = isLeader,
            _isBand = isBand,
            _bandId = bandId
        )

        userSessionEntity.toBusinessUserSession()
    }

    override suspend fun updateCurrentUserInfo(updatedUserSession: UserSession) {
        val newSessionEntity = updatedUserSession.toDataStoreUserSessionEntity()

        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.IS_LOGGED_IN] = newSessionEntity._isLogged
            pref[UserPreferencesKeys.USER_ID] = newSessionEntity._userId
            pref[UserPreferencesKeys.USER_NAME] = newSessionEntity._userName
            pref[UserPreferencesKeys.USER_NICKNAME] = newSessionEntity._userNickname
            pref[UserPreferencesKeys.USER_PROFILE_IMAGE] = newSessionEntity._userProfileImage
            pref[UserPreferencesKeys.USER_DESCRIPTION] = newSessionEntity._userDescription
            pref[UserPreferencesKeys.USER_INSTRUMENT] = newSessionEntity._userInstrument
            pref[UserPreferencesKeys.USER_REGION] = newSessionEntity._userRegion
            pref[UserPreferencesKeys.IS_LEADER] = newSessionEntity._isLeader
            pref[UserPreferencesKeys.IS_BAND] = newSessionEntity._isBand
            pref[UserPreferencesKeys.BAND_ID] = newSessionEntity._bandId
        }
    }

    override suspend fun clearUserSession() {
        context.dataStore.edit { it.clear() }
    }
}