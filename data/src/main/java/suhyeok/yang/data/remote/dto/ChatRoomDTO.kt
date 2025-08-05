package suhyeok.yang.data.remote.dto

import com.google.firebase.firestore.IgnoreExtraProperties
import com.yang.business.model.User
import java.time.LocalDateTime

@IgnoreExtraProperties
data class ChatRoomDTO(
    val _chatRoomId: String = "",
    val _chatRoomName: String = "",
    var _roomImageUrl: String = "",
    val _participants: List<UserDTO> = emptyList(),
    val _participantIds: List<String> = emptyList(),
    var _lastMessage: String = "",
    var _lastMessageTimestamp: String = "",
    var _unreadMessageCount: Int = 0,
    val _createdAt: String = "",
    val _isGroupChat: Boolean = false,
    val _messages: List<MessageDTO> = emptyList()
)
