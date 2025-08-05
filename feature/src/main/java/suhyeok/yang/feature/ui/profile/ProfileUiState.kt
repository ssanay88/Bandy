package suhyeok.yang.feature.ui.profile

import com.yang.business.common.BandyException
import com.yang.business.enums.Instrument
import suhyeok.yang.feature.common.base.UiState

data class ProfileUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    val profileImageUrl: String = "",
    val userNickname: String = "",
    val userInstrument: Instrument = Instrument.VOCAL,
    val userDescription: String = "",

) : UiState(overallLoading, globalError)