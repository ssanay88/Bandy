package suhyeok.yang.feature.ui.home

import com.yang.business.common.BandyException
import com.yang.business.model.Band
import com.yang.business.model.HomeTopBanner
import com.yang.business.model.Posting
import suhyeok.yang.feature.common.base.UiState

data class HomeUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    // TopBanner
    val topBannerList: List<HomeTopBanner> = emptyList(),
    val isTopBannerLoading: Boolean = false,
    val topBannerErrorMessage: String? = null,

    // PopularBand
    val popularBandList: List<Band> = emptyList(),
    val isPopularBandLoading: Boolean = false,
    val popularBandErrorMessage: String? = null,

    // Posting
    val postingList: List<Posting> = emptyList(),
    val isPostingLoading: Boolean = false,
    val postingErrorMessage: String? = null,
) : UiState(overallLoading, globalError)