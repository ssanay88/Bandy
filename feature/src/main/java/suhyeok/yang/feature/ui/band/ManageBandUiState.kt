package suhyeok.yang.feature.ui.band

import com.yang.business.common.BandyException
import com.yang.business.model.Region
import com.yang.business.model.User
import suhyeok.yang.feature.common.base.UiState

data class ManageBandUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    val bandProfileImageUrl: String = "",
    val bandName: String = "",
    val bandRegion: Region = Region(),
    val bandMemberList: List<User> = emptyList(),
    val bandIntroduce: String = ""

) : UiState(overallLoading, globalError)