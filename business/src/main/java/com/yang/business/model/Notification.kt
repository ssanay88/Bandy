package com.yang.business.model

import com.yang.business.enums.NotificationType
import java.time.LocalDateTime

data class Notification(
    val notificationId: String,
    val title: String,
    val content: String,
    var isRead: Boolean,
    val notificationType: NotificationType,
    val relatedItemId: String,
    val senderId: String,
    val receiverId: String,
    val createdAt: LocalDateTime
)
