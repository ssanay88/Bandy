package suhyeok.yang.feature.ui.myband

import com.yang.business.common.BandyException
import com.yang.business.model.Band
import suhyeok.yang.feature.common.base.UiState

data class MyBandUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    val hasBand: Boolean = false,

    val myBand: Band? = null,
    val isMyBandLoading: Boolean = false,
    val myBandErrorMessage: String? = null
) : UiState(overallLoading, globalError)