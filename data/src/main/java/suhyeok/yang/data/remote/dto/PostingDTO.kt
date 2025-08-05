package suhyeok.yang.data.remote.dto

import com.google.firebase.firestore.IgnoreExtraProperties
import com.yang.business.model.Comment
import com.yang.business.model.PostingAuthorInfo
import com.yang.business.enums.PostingType
import java.time.LocalDateTime

@IgnoreExtraProperties
data class PostingDTO(
    val _postingId: String = "",
    var _title: String = "",
    var _content: String = "",
    var _postingType: PostingType = PostingType.FREE,
    val _postingAuthorInfo: PostingAuthorInfo = PostingAuthorInfo(),
    var _viewCount: Int = 0,
    var _commentCount: Int = 0,
    val _createdAt: String = "",
    var _updatedAt: String = "",
    var _comments: List<CommentDTO> = listOf()
)