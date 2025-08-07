package suhyeok.yang.feature.ui.band

import com.yang.business.common.BandyException
import com.yang.business.model.User
import suhyeok.yang.feature.common.base.UiState

data class CreateBandUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    val bandLeaderId: String = "",
    val matchedUsers: List<User> = emptyList(),

    val bandProfileImageUrl: String = "",
    val bandCoverImageUrl: String = "",
    val bandName: String = "",
    val bandSido: String = "",
    val bandSigungu: String = "",
    val bandMemberList: List<User> = emptyList(),
    val bandIntroduce: String = "",
    val bandYoutubeLink: String = "",
    val bandInstagramLink: String = "",
    val bandSpotifyLink: String = ""
) : UiState(overallLoading, globalError)