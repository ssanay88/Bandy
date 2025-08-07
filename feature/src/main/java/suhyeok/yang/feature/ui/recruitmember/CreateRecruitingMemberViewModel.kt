package suhyeok.yang.feature.ui.recruitmember

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.enums.AgeGroup
import com.yang.business.enums.Gender
import com.yang.business.enums.Instrument
import com.yang.business.enums.RecruitingStatus
import com.yang.business.enums.SkillLevel
import com.yang.business.model.RecruitPosting
import com.yang.business.model.Region
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.recruitposting.RecruitPostingUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class CreateRecruitingMemberViewModel(
    val userSessionUseCases: UserSessionUseCases,
    val bandUseCases: BandUseCases,
    val recruitPostingUseCases: RecruitPostingUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateRecruitingMemberUiState())
    val uiState = _uiState.asStateFlow()

    fun createRecruitingMemberPosting() {
        viewModelScope.launch {
            // 내 정보 불러오기 -> 밴드 정보 가져오기
            userSessionUseCases.getUserSession().collectLatest { user ->
                bandUseCases.readBand(user.bandId).collectLatest { result ->
                    when (result) {
                        is DataResourceResult.Success -> {
                            val newRecruitingMemberPosting = with(_uiState.value) {
                                RecruitPosting(
                                    recruitPostingId = "recruitPostingId_${UUID.randomUUID()}",
                                    title = recruitingInfoTitle,
                                    content = recruitingInfoContent,
                                    postedBandId = result.data.bandId,
                                    postedBandName = result.data.bandName,
                                    postedBandImageUrl = result.data.bandProfileImageUrl,
                                    authorId = user.userId,
                                    targetAgeGroups = targetAgeGroup.ifEmpty { listOf(AgeGroup.ALL_AGES) },
                                    targetGender = targetGender,
                                    targetRegion = Region(
                                        sido = targetSido,
                                        sigungu = targetSigungu
                                    ),
                                    targetSkillLevel = targetSkillLevel,
                                    targetInstrument = targetInstrument,
                                    recruitingStatus = RecruitingStatus.ACTIVE,
                                    createdAt = LocalDateTime.now(),
                                    updatedAt = LocalDateTime.now(),
                                    viewCount = 0,
                                    commentCount = 0
                                    )
                            }
                            recruitPostingUseCases.createRecruitPosting(newRecruitingMemberPosting).collectLatest {result ->
                                when (result) {
                                    is DataResourceResult.Success -> {
                                        _uiState.update { it.copy(overallLoading = false) }
                                    }
                                    is DataResourceResult.Failure -> {
                                        _uiState.update { it.copy(overallLoading = false) }
                                    }
                                    is DataResourceResult.Loading -> {
                                        _uiState.update { it.copy(overallLoading = true) }
                                    }
                                    else -> {}
                                }
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    fun validateRecruitingMemberPosting(): RecruitPostingValidationResult {
        return when {
            _uiState.value.targetInstrument == Instrument.NOTHING -> RecruitPostingValidationResult.InstrumentUnselected

            _uiState.value.recruitingInfoTitle.isEmpty() -> RecruitPostingValidationResult.PostingTitleEmpty

            _uiState.value.recruitingInfoContent.isEmpty() -> RecruitPostingValidationResult.PostingContentEmpty

            else -> RecruitPostingValidationResult.Success
        }
    }

    fun setTargetInstrument(newTargetInstrument: Instrument) {
        _uiState.update { it.copy(targetInstrument = newTargetInstrument) }
    }

    fun updateAgeGroup(ageGroup: AgeGroup) {
        val currentTargetAgeGroup = _uiState.value.targetAgeGroup.toMutableList()
        val updateTargetAgeGroup = if (currentTargetAgeGroup.contains(ageGroup)) {
            currentTargetAgeGroup - ageGroup
        } else {
            currentTargetAgeGroup + ageGroup
        }
        _uiState.update {
            it.copy(targetAgeGroup = updateTargetAgeGroup)
        }
    }

    fun setTargetSido(newTargetSido: String) {
        _uiState.update { it.copy(targetSido = newTargetSido) }
    }

    fun setTargetSigungu(newTargetSigungu: String) {
        _uiState.update { it.copy(targetSigungu = newTargetSigungu) }
    }

    fun setTargetGender(newTargetGender: Gender) {
        _uiState.update { it.copy(targetGender = newTargetGender) }
    }

    fun setTargetSkillLevel(newTargetSkillLevel: SkillLevel) {
        _uiState.update { it.copy(targetSkillLevel = newTargetSkillLevel) }
    }

    fun setRecruitingInfoTitle(newRecruitingInfoTitle: String) {
        _uiState.update { it.copy(recruitingInfoTitle = newRecruitingInfoTitle) }
    }

    fun setRecruitingInfoContent(newRecruitingInfoContent: String) {
        _uiState.update { it.copy(recruitingInfoContent = newRecruitingInfoContent) }
    }


}