package suhyeok.yang.feature.ui.profile

import com.yang.business.common.BandyException
import suhyeok.yang.feature.common.base.UiState

data class ProfileRegUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,
) : UiState(overallLoading, globalError)