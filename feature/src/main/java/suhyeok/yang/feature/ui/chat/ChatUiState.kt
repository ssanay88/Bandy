package suhyeok.yang.feature.ui.chat

import com.yang.business.common.BandyException
import com.yang.business.model.ChatRoom
import suhyeok.yang.feature.common.base.UiState

data class ChatUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    val chatRooms: List<ChatRoom> = emptyList(),
    val currentUserId: String = ""

) : UiState(overallLoading, globalError)