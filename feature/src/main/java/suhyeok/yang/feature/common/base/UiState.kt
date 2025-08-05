package suhyeok.yang.feature.common.base

import com.yang.business.common.BandyException

open class UiState(
    open val overallLoading: Boolean = false,
    open val globalError: BandyException? = null
)
