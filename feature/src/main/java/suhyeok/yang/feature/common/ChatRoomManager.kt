package suhyeok.yang.feature.common

import com.yang.business.model.ChatRoom
import com.yang.business.model.User
import com.yang.business.repository.UserRepository
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class ChatRoomManager @Inject constructor(
    private val userRepository: UserRepository
) {

    private fun newChatRoomId(): String = "chat_${UUID.randomUUID()}"


    // 채팅방 새로 생성
    fun createNewChatRoom(
        chatRoomName: String,
        roomImageUrl: String,
        participants: List<User>,
        ): ChatRoom = ChatRoom(
            chatRoomId = newChatRoomId(),
            chatRoomName = chatRoomName,
            roomImageUrl = roomImageUrl,
            participants = participants,
            participantIds = participants.map { it.userId },
            lastMessage = null,
            lastMessageTimestamp = null,
            unreadMessageCount = 0,
            createdAt = LocalDateTime.now(),
            isGroupChat = participants.size > 2,
            messages = emptyList()
        )


    // 채팅방 이미지 변경

    // 채팅방 제거

    // 채팅방 멤버 추가


}