package suhyeok.yang.feature.ui.profile

import com.yang.business.common.BandyException
import com.yang.business.enums.Instrument
import suhyeok.yang.feature.common.base.UiState

data class ProfileUpdateUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    val myProfileImageUrl: String = "",
    val myProfileNickname: String = "",
    val myProfileInstrument: Instrument = Instrument.NOTHING,
    val myProfileSido: String = "",
    val myProfileSigungu: String = "",
    val myProfileIntroduce: String = ""
) : UiState(overallLoading, globalError)