package suhyeok.yang.data.remote.dto

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class CommentDTO(
    val _commentId: String = "",
    val _postingId: String = "",
    val _authorId: String = "",
    val _content: String = "",
    val _createdAt: String = "",
    var _updatedAt: String = ""
)