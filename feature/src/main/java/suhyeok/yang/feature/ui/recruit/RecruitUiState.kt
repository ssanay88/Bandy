package suhyeok.yang.feature.ui.recruit

import com.yang.business.common.BandyException
import com.yang.business.model.Band
import com.yang.business.model.RecruitPosting
import suhyeok.yang.feature.common.base.UiState

data class RecruitUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    // Recruiting Band
    val recruitingBandList: List<Band> = emptyList(),
    val isRecruitingBandListLoading: Boolean = false,
    val recruitingBandListErrorMessage: String? = null,

    // Recruit Posting
    val recruitPostingList: List<RecruitPosting> = emptyList(),
    val isRecruitPostingListLoading: Boolean = false,
    val recruitPostingListErrorMessage: String? = null,
) : UiState(overallLoading, globalError)
