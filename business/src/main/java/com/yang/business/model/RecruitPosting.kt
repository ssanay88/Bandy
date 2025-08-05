package com.yang.business.model

import com.yang.business.enums.AgeGroup
import com.yang.business.enums.Gender
import com.yang.business.enums.Instrument
import com.yang.business.enums.RecruitingStatus
import java.time.LocalDateTime

data class RecruitPosting(
    val recruitPostingId: String,
    var title: String,
    var content: String,
    val postedBandId: String,
    val postedBandName: String,
    val postedBandImageUrl: String,
    val authorId: String,
    var targetAgeGroups: List<AgeGroup>,
    var targetGender: Gender,
    var targetRegion: Region,
    var targetInstrument: Instrument,
    var recruitingStatus: RecruitingStatus,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var viewCount: Int,
    var commentCount: Int
) {
    fun targetAgeGroupToString(): String = if (targetAgeGroups.isEmpty()) "전체" else targetAgeGroups.joinToString(", ") { it.name }

    fun targetGenderToString(): String = when (targetGender) {
        Gender.MALE -> "남성"
        Gender.FEMALE -> "여성"
        else -> "전체"
    }
}
