package suhyeok.yang.feature.ui.posting

import com.yang.business.common.BandyException
import com.yang.business.enums.PostingType
import suhyeok.yang.feature.common.base.UiState

data class CreatePostingUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    val postingType: PostingType = PostingType.FREE,
    val postingTitle: String = "",
    val postingContent: String = ""
) : UiState(overallLoading, globalError)