package suhyeok.yang.feature.ui.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.model.Message
import com.yang.business.repository.ChatRoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class ChatRoomViewModel(
    private val chatRoomRepository: ChatRoomRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatRoomUiState())
    val uiState = _uiState.asStateFlow()

    fun observeChatRoom(chatRoomId: String) {
        viewModelScope.launch {
            chatRoomRepository.observeMessages(chatRoomId).collectLatest { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(messages = result.data)
                        }

                        else -> it
                    }
                }
            }
        }
    }

    fun getChatRoomParticipantsInfo(chatRoomId: String) {
        viewModelScope.launch {
            chatRoomRepository.getChatParticipants(chatRoomId).collectLatest { participants ->
                _uiState.update { it.copy(participants = participants) }
            }
        }

    }

    fun sendMessage(chatRoomId: String, senderId: String, content: String) {
        val newMessage = Message(
            messageId = "message_${UUID.randomUUID()}",
            senderId = senderId,
            chatRoomId = chatRoomId,
            content = content,
            timestamp = LocalDateTime.now(),
            readUserIds = listOf(senderId)
        )

        viewModelScope.launch {
            chatRoomRepository.sendMessage(chatRoomId, newMessage).collectLatest {
                when (it) {
                    is DataResourceResult.Success -> {
                        Log.d("tngur", "send message success - ${newMessage.content}")
                    }

                    is DataResourceResult.Failure -> {
                        Log.d("tngur", "send message failure - ${newMessage.content}")
                    }

                    else -> {}
                }
            }
        }
    }



}