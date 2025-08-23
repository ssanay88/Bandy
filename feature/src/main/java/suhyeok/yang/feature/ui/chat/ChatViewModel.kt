package suhyeok.yang.feature.ui.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.repository.DataStoreRepository
import com.yang.business.usecase.chatroom.ChatRoomUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val chatRoomUseCases: ChatRoomUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    fun loadUsersChatRooms() {
        viewModelScope.launch {
            val loggedUserId = dataStoreRepository.userId.first()

            chatRoomUseCases.readChatRoomUseCase(userId = loggedUserId).collectLatest { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(
                                overallLoading = false,
                                chatRooms = result.data,
                                currentUserId = loggedUserId
                            )
                        }
                        is DataResourceResult.Failure -> {
                            it.copy(
                                overallLoading = false,
                                currentUserId = loggedUserId
                            )
                        }
                        is DataResourceResult.Loading -> {
                            it.copy(
                                overallLoading = true,
                                currentUserId = loggedUserId
                            )
                        }
                        else -> { it.copy(currentUserId = loggedUserId) }
                    }
                }
            }
        }
    }
}