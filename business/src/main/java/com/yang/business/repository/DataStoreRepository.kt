package com.yang.business.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    val isLoggedIn: Flow<Boolean>
    suspend fun setIsLoggedIn(isLoggedIn: Boolean)

    val userId: Flow<String>
    suspend fun setUserId(userId: String)

    val userName: Flow<String>
    suspend fun setUserName(userName: String)

    val userNickname: Flow<String>
    suspend fun setUserNickname(userNickname: String)

    val userProfileImage: Flow<String>
    suspend fun setUserProfileImage(userProfileImage: String)

    val userDescription: Flow<String>
    suspend fun setUserDescription(userDescription: String)

    val userInstrument: Flow<String>
    suspend fun setUserInstrument(userInstrument: String)

    val userRegion: Flow<String>
    suspend fun setUserRegion(userRegion: String)

    val userGender: Flow<String>
    suspend fun setUserGender(userGender: String)

    val userSkillLevel: Flow<String>
    suspend fun setUserSkillLevel(userSkillLevel: String)

    val userBirthDate: Flow<String>
    suspend fun setUserBirthDate(userBirthDate: String)

    val isLeader: Flow<Boolean>
    suspend fun setIsLeader(isLeader: Boolean)

    val isBand: Flow<Boolean>
    suspend fun setIsBand(isBand: Boolean)

    val bandId: Flow<String>
    suspend fun setBandId(bandId: String)

    suspend fun clearUserSession()
}