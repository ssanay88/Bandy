package suhyeok.yang.feature.ui.recruitmember

import com.yang.business.common.BandyException
import com.yang.business.enums.AgeGroup
import com.yang.business.enums.Gender
import com.yang.business.enums.Instrument
import com.yang.business.enums.SkillLevel
import suhyeok.yang.feature.common.base.UiState

data class CreateRecruitingMemberUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    val targetInstrument: Instrument = Instrument.NOTHING,
    val targetAgeGroup: List<AgeGroup> = emptyList(),
    val targetSido: String = "",
    val targetSigungu: String = "",
    val targetGender: Gender = Gender.ALL,
    val targetSkillLevel: SkillLevel = SkillLevel.BEGINNER,
    val recruitingInfoTitle: String = "",
    val recruitingInfoContent: String = ""
) : UiState(overallLoading, globalError)