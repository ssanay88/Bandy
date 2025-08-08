package suhyeok.yang.feature.ui.profile

import com.yang.business.common.BandyException
import com.yang.business.model.Posting
import suhyeok.yang.feature.common.base.UiState

data class PostingHistoryUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    // My Posting
    val myPostingList: List<Posting> = emptyList(),
    val isMyPostingListLoading: Boolean = false,

    // Commented Posting
    val commentedPostingList: List<Posting> = emptyList(),
    val isCommentedPostingListLoading: Boolean = false
) : UiState(overallLoading, globalError)