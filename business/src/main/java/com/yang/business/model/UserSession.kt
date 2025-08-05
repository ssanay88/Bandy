package com.yang.business.model

import com.yang.business.enums.Instrument

data class UserSession(
    val isLogged: Boolean,
    val userId: String,
    val userName: String,
    val userNickname: String,
    val userProfileImage: String,
    val userDescription: String,
    val userInstrument: Instrument,
    val isLeader: Boolean,
    val isBand: Boolean,
    val bandId: String,
)