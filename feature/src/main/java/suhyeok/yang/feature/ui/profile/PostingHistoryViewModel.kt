package suhyeok.yang.feature.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.usecase.postinghistory.PostingHistoryUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostingHistoryViewModel @Inject constructor(
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
        viewModelScope.launch {
            postingHistoryUseCases.readMyPosting(userId).collectLatest { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(
                                overallLoading = false,
                                myPostingList = result.data
                            )
                        }

                        is DataResourceResult.Loading -> {
                            it.copy(overallLoading = true)
                        }

                        else -> {
                            it
                        }
                    }
                }
            }
        }
    }

    fun loadCommentedPostingList(userId: String) {
        viewModelScope.launch {
            postingHistoryUseCases.readCommentedPosting(userId).collectLatest { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(
                                overallLoading = false,
                                commentedPostingList = result.data
                            )
                        }

                        is DataResourceResult.Loading -> {
                            it.copy(overallLoading = true)
                        }

                        else -> {
                            it
                        }

                    }
                }
            }
        }
    }
}