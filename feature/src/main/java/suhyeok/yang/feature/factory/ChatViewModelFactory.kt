package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.chatroom.ChatRoomUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.feature.ui.chat.ChatViewModel

class ChatViewModelFactory(
    private val userSessionUseCases: UserSessionUseCases,
    private val chatRoomUseCases: ChatRoomUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(userSessionUseCases, chatRoomUseCases) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}