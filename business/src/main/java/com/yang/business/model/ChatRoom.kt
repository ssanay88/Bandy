package com.yang.business.model

import java.time.LocalDateTime

data class ChatRoom(
    val chatRoomId: String,
    val chatRoomName: String,
    var roomImageUrl: String,
    val participants: List<User>,
    val participantIds: List<String>,
    var lastMessage: String?,
    var lastMessageTimestamp: LocalDateTime?,
    var unreadMessageCount: Int,
    val createdAt: LocalDateTime,
    val isGroupChat: Boolean,
    val messages: List<Message>
)
