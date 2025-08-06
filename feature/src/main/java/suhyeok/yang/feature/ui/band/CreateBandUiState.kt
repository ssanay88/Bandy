package suhyeok.yang.feature.ui.band

import com.yang.business.common.BandyException
import com.yang.business.model.User
import suhyeok.yang.feature.common.base.UiState

data class CreateBandUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    val bandLeaderId: String = "",
    val matchedUsers: List<User> = emptyList()
) : UiState(overallLoading, globalError)