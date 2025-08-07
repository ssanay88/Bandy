package suhyeok.yang.feature.ui.recruit

import com.yang.business.common.BandyException
import com.yang.business.enums.AgeGroup
import com.yang.business.enums.Gender
import com.yang.business.enums.Instrument
import com.yang.business.enums.RecruitingStatus
import com.yang.business.enums.SkillLevel
import com.yang.business.model.Region
import suhyeok.yang.feature.common.base.UiState
import java.time.LocalDateTime

data class RecruitingMemberUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    var title: String = "",
    var content: String = "",
    val postedBandId: String = "",
    val postedBandName: String = "",
    val postedBandImageUrl: String = "",
    val authorId: String = "",
    var targetAgeGroups: List<AgeGroup> = emptyList(),
    var targetGender: Gender = Gender.ALL,
    var targetRegion: Region = Region("", ""),
    var targetSkillLevel: SkillLevel = SkillLevel.BEGINNER,
    var targetInstrument: Instrument = Instrument.NOTHING,
    var recruitingStatus: RecruitingStatus = RecruitingStatus.ACTIVE,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var viewCount: Int = 0,
    var commentCount: Int = 0

) : UiState(overallLoading, globalError)