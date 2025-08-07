package com.yang.business.model

import com.yang.business.enums.AgeGroup
import com.yang.business.enums.Gender
import com.yang.business.enums.Instrument
import com.yang.business.enums.RecruitingStatus
import com.yang.business.enums.SkillLevel
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
    var targetSkillLevel: SkillLevel,
    var targetInstrument: Instrument,
    var recruitingStatus: RecruitingStatus,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var viewCount: Int = 0,
    var commentCount: Int = 0
)
