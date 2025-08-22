package suhyeok.yang.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.yang.business.repository.DataStoreRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): DataStoreRepository {
    companion object {
        const val USER_SESSION_DATASTORE = "user_session_datastore"
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_SESSION_DATASTORE)

    override val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { pref ->
        pref[UserPreferencesKeys.IS_LOGGED_IN] ?: false
    }

    override suspend fun setIsLoggedIn(isLoggedIn: Boolean) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.IS_LOGGED_IN] = isLoggedIn
        }
    }

    override val userId: Flow<String> = context.dataStore.data.map { pref ->
        pref[UserPreferencesKeys.USER_ID] ?: ""
    }

    override suspend fun setUserId(userId: String) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.USER_ID] = userId
        }
    }

    override val userName: Flow<String> = context.dataStore.data.map { pref ->
        pref[UserPreferencesKeys.USER_NAME] ?: ""
    }

    override suspend fun setUserName(userName: String) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.USER_NAME] = userName
        }
    }

    override val userNickname: Flow<String> = context.dataStore.data.map { pref ->
        pref[UserPreferencesKeys.USER_NICKNAME] ?: ""
    }

    override suspend fun setUserNickname(userNickname: String) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.USER_NICKNAME] = userNickname
        }
    }

    override val userProfileImage: Flow<String> = context.dataStore.data.map { pref ->
        pref[UserPreferencesKeys.USER_PROFILE_IMAGE] ?: ""
    }

    override suspend fun setUserProfileImage(userProfileImage: String) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.USER_PROFILE_IMAGE] = userProfileImage
        }
    }

    override val userDescription: Flow<String> = context.dataStore.data.map { pref ->
        pref[UserPreferencesKeys.USER_DESCRIPTION] ?: ""
    }

    override suspend fun setUserDescription(userDescription: String) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.USER_DESCRIPTION] = userDescription
        }
    }

    override val userInstrument: Flow<String> = context.dataStore.data.map { pref ->
        pref[UserPreferencesKeys.USER_INSTRUMENT] ?: ""
    }

    override suspend fun setUserInstrument(userInstrument: String) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.USER_INSTRUMENT] = userInstrument
        }
    }

    override val userRegion: Flow<String> = context.dataStore.data.map { pref ->
        pref[UserPreferencesKeys.USER_REGION] ?: ""
    }

    override suspend fun setUserRegion(userRegion: String) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.USER_REGION] = userRegion
        }
    }

    override val isLeader: Flow<Boolean> = context.dataStore.data.map { pref ->
        pref[UserPreferencesKeys.IS_LEADER] ?: false
    }

    override suspend fun setIsLeader(isLeader: Boolean) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.IS_LEADER] = isLeader
        }
    }

    override val isBand: Flow<Boolean> = context.dataStore.data.map { pref ->
        pref[UserPreferencesKeys.IS_BAND] ?: false
    }

    override suspend fun setIsBand(isBand: Boolean) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.IS_BAND] = isBand
        }
    }

    override val bandId: Flow<String> = context.dataStore.data.map { pref ->
        pref[UserPreferencesKeys.BAND_ID] ?: ""
    }

    override suspend fun setBandId(bandId: String) {
        context.dataStore.edit { pref ->
            pref[UserPreferencesKeys.BAND_ID] = bandId
        }
    }

    override suspend fun clearUserSession() {
        context.dataStore.edit { it.clear() }
    }
}