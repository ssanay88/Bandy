package com.yang.business.model

import com.yang.business.enums.PostingType
import java.time.LocalDateTime

data class Posting(
    val postingId: String,
    var title: String,
    var content: String,
    var postingType: PostingType,
    val postingAuthorInfo: PostingAuthorInfo,
    var viewCount: Int,
    var commentCount: Int,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var comments: List<Comment>
)
