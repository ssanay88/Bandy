package suhyeok.yang.feature.ui.posting

import com.yang.business.common.BandyException
import com.yang.business.enums.PostingType
import com.yang.business.model.Comment
import com.yang.business.model.PostingAuthorInfo
import suhyeok.yang.feature.common.base.UiState
import java.time.LocalDateTime

data class PostingDetailUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    val postingId: String = "",
    val postingTitle: String = "",
    val postingContent: String = "",
    val postingType: PostingType = PostingType.FREE,
    val postingAuthorInfo: PostingAuthorInfo = PostingAuthorInfo(),
    val postingViewCount: Int = 0,
    val postingCommentCount: Int = 0,
    val postingCreatedAt: LocalDateTime = LocalDateTime.now(),
    val postingUpdatedAt: LocalDateTime = LocalDateTime.now(),
    val postingComments: List<Comment> = emptyList()

) : UiState(overallLoading, globalError)