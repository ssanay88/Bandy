package suhyeok.yang.feature.ui.chat

import com.yang.business.common.BandyException
import com.yang.business.model.Message
import com.yang.business.model.User
import suhyeok.yang.feature.common.base.UiState

data class ChatRoomUiState(
    override val overallLoading: Boolean = false,
    override val globalError: BandyException? = null,

    val messages: List<Message> = emptyList(),
    val participants: List<User> = emptyList()
) : UiState(overallLoading, globalError)