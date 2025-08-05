package suhyeok.yang.data.remote.dto

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class MessageDTO(
    val _messageId: String = "",
    val _senderId: String = "",
    val _chatRoomId: String = "",
    val _content: String = "",
    val _timestamp: String = "",
    val _unreadUserIds: List<String> = emptyList()
)