package com.yang.business.model

import java.time.LocalDateTime

data class Message(
    val messageId: String,
    val senderId: String,
    val chatRoomId: String,
    val content: String,
    val timestamp: LocalDateTime,
    val readUserIds: List<String>
)
