package suhyeok.yang.feature.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.usecase.postinghistory.PostingHistoryUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PostingHistoryViewModel(
    private val userSessionUseCases: UserSessionUseCases,
    private val postingHistoryUseCases: PostingHistoryUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(PostingHistoryUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initPostingHistory()
    }

    private fun initPostingHistory() {
        viewModelScope.launch {
            userSessionUseCases.getUserSession().collectLatest { userSession ->
                val userId = userSession.userId
                loadMyPostingList(userId)
                loadCommentedPostingList(userId)
            }
        }
    }

    fun loadMyPostingList(userId: String) {

    }

    fun loadCommentedPostingList(userId: String) {

    }
}