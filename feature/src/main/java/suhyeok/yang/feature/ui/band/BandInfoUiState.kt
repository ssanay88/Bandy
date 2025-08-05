package suhyeok.yang.feature.ui.band

import com.yang.business.common.BandyException
import com.yang.business.model.Band
import suhyeok.yang.feature.common.base.UiState

data class BandInfoUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    val band: Band? = null,
    val isBandLoading: Boolean = false,
    val isBandLoaded: Boolean = false,
    val bandErrorMessage: String? = null,
) : UiState(overallLoading, globalError)