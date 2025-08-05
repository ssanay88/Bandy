package com.yang.business.model

import java.time.LocalDateTime

data class Comment(
    val commentId: String,
    val postingId: String,
    val authorId: String,
    val content: String,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)
