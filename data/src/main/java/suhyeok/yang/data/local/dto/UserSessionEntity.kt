package suhyeok.yang.data.local.dto

data class UserSessionEntity(
    val _isLogged: Boolean,
    val _userId: String,
    val _userName: String,
    val _userNickname: String,
    val _userProfileImage: String,
    val _userDescription: String,
    val _userInstrument: String,
    val _isLeader: Boolean,
    val _isBand: Boolean,
    val _bandId: String
)