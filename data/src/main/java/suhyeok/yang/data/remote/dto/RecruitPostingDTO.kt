package suhyeok.yang.data.remote.dto

import com.google.firebase.firestore.IgnoreExtraProperties
import com.yang.business.enums.AgeGroup
import com.yang.business.enums.Gender
import com.yang.business.enums.Instrument
import com.yang.business.enums.RecruitingStatus
import com.yang.business.model.Region

@IgnoreExtraProperties
data class RecruitPostingDTO(
    val _recruitPostingId: String = "",
    var _title: String = "",
    var _content: String = "",
    val _postedBandId: String = "",
    var _postedBandName: String = "",
    var _postedBandImageUrl: String = "",
    val _authorId: String = "",
    var _targetAgeGroups: List<AgeGroup> = listOf(),
    var _targetGender: Gender = Gender.ALL,
    var _targetRegion: String = "",
    var _targetSkillLevel: String = "",
    var _targetInstrument: Instrument = Instrument.VOCAL,
    var _recruitingStatus: RecruitingStatus = RecruitingStatus.ACTIVE,
    val _createdAt: String = "",
    var _updatedAt: String = "",
    var _viewCount: Int = 0,
    var _commentCount: Int = 0
)
