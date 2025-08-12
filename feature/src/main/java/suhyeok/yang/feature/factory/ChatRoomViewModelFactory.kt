package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.repository.ChatRoomRepository
import suhyeok.yang.feature.ui.chat.ChatRoomViewModel

class ChatRoomViewModelFactory(
    private val chatRoomRepository: ChatRoomRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatRoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatRoomViewModel(chatRoomRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}