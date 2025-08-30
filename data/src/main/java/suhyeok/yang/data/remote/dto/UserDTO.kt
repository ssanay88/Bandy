package suhyeok.yang.data.remote.dto

import com.google.firebase.firestore.IgnoreExtraProperties
import com.yang.business.enums.Gender
import com.yang.business.enums.Instrument
import com.yang.business.enums.SkillLevel

@IgnoreExtraProperties
data class UserDTO(
    val _userId: String = "",
    val _userName: String = "",
    var _nickName: String = "",
    var _profileImageUrl: String = "",
    var _instrument: Instrument = Instrument.VOCAL,
    var _userDescription: String = "",
    var _region: String = "",
    val _gender: Gender = Gender.MALE,
    var _skillLevel: SkillLevel = SkillLevel.BEGINNER,
    val _birthDate: String = "",
    var _bandId: String = "",
    var _hasBand: Boolean = false,
    var _isLeader: Boolean = false,
    val _signupDate: String = "",
    var _lastLoginDate: String = ""
)
