package com.yang.business.model

import com.yang.business.enums.Gender
import com.yang.business.enums.Instrument
import com.yang.business.enums.SkillLevel
import java.time.LocalDate
import java.time.LocalDateTime

data class User(
    val userId: String = "",
    val userName: String = "",
    var nickName: String = "",
    var profileImageUrl: String = "",
    var instrument: Instrument = Instrument.VOCAL,
    var userDescription: String = "",
    var region: Region = Region(),
    val gender: Gender = Gender.MALE,
    var skillLevel: SkillLevel = SkillLevel.BEGINNER,
    val birthDate: LocalDate = LocalDate.now(),
    var bandId: String = "",
    var isLeader: Boolean = false,
    val signupDate: LocalDate = LocalDate.now(),
    var lastLoginDate: LocalDateTime = LocalDateTime.now()
)
