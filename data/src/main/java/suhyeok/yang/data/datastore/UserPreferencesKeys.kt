package suhyeok.yang.data.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferencesKeys {
    // Login State
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")

    // User Info
    val USER_ID = stringPreferencesKey("user_id")
    val USER_NAME = stringPreferencesKey("user_name")
    val USER_NICKNAME = stringPreferencesKey("user_nickname")
    val USER_PROFILE_IMAGE = stringPreferencesKey("user_profile_image")
    val USER_DESCRIPTION = stringPreferencesKey("user_description")
    val USER_INSTRUMENT = stringPreferencesKey("user_instrument")
    val USER_SIDO = stringPreferencesKey("user_sido")
    val USER_SIGUNGU = stringPreferencesKey("user_sigungu")
    val USER_GENDER = stringPreferencesKey("user_gender")
    val USER_SKILL_LEVEL = stringPreferencesKey("user_skill_level")
    val USER_BIRTH_DATE = stringPreferencesKey("user_birth_date")
    val IS_LEADER = booleanPreferencesKey("is_leader")
    val IS_BAND = booleanPreferencesKey("is_band")
    val BAND_ID = stringPreferencesKey("band_id")

}