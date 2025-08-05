package suhyeok.yang.feature.ui.chat

import androidx.lifecycle.ViewModel
import com.yang.business.usecase.chatroom.ChatRoomUseCases

class ChatRoomViewModel(
    private val chatRoomUseCases: ChatRoomUseCases
) : ViewModel() {
}