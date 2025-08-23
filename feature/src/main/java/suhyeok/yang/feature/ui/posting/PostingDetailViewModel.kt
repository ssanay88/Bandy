package suhyeok.yang.feature.ui.posting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.enums.PostingType
import com.yang.business.model.Comment
import com.yang.business.model.Posting
import com.yang.business.model.PostingAuthorInfo
import com.yang.business.model.User
import com.yang.business.repository.DataStoreRepository
import com.yang.business.usecase.posting.PostingUseCases
import com.yang.business.usecase.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PostingDetailViewModel @Inject constructor(
    private val postingUserCases: PostingUseCases,
    private val userUseCases: UserUseCases,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostingDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _currentUserId = MutableStateFlow("")
    val currentUserId = _currentUserId.asStateFlow()

    private val _commentAuthorMap = MutableStateFlow<Map<String, User>>(emptyMap())
    val commentAuthorMap: StateFlow<Map<String, User>> = _commentAuthorMap.asStateFlow()

    init {
        getCurrentUserId()
    }

    fun loadPostingDetail(postingId: String) {
        viewModelScope.launch {
            postingUserCases.readPostingDetail(postingId).collect { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(
                                overallLoading = false,
                                postingId = result.data.postingId,
                                postingTitle = result.data.title,
                                postingContent = result.data.content,
                                postingType = result.data.postingType,
                                postingAuthorInfo = result.data.postingAuthorInfo,
                                postingViewCount = result.data.viewCount + 1,
                                postingCommentCount = result.data.commentCount,
                                postingCreatedAt = result.data.createdAt,
                                postingUpdatedAt = result.data.updatedAt,
                                postingComments = result.data.comments
                            )
                        }

                        is DataResourceResult.Failure -> {
                            it.copy(
                                overallLoading = false,
                                postingId = "",
                                postingTitle = "",
                                postingContent = "",
                                postingType = PostingType.FREE,
                                postingAuthorInfo = PostingAuthorInfo(),
                                postingViewCount = 0,
                                postingCommentCount = 0,
                                postingCreatedAt = LocalDateTime.now(),
                                postingUpdatedAt = LocalDateTime.now(),
                                postingComments = emptyList()
                            )
                        }

                        is DataResourceResult.Loading -> {
                            it.copy(
                                overallLoading = true
                            )
                        }

                        else -> {
                            it
                        }
                    }
                }
            }
        }
    }

    fun getCurrentUserId() {
        viewModelScope.launch {
            _currentUserId.update { dataStoreRepository.userId.first() }
        }
    }

    fun getCommentAuthorInfo(authorId: String) {
        if (_commentAuthorMap.value.containsKey(authorId)) return

        viewModelScope.launch {
            userUseCases.readUser(authorId).collectLatest { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        _commentAuthorMap.update { it + (authorId to result.data) }
                    }

                    is DataResourceResult.Failure -> {
                        // 실패했을 경우 처리 (예: 기본 유저 정보 설정?)
                    }

                    is DataResourceResult.Loading -> {
                        // 로딩 상태 업데이트 시 필요하면 처리
                    }
                    else -> {}
                }
            }
        }
    }


    fun commentSubmit(newComment: Comment) {
        // UI에 추가
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    postingCommentCount = it.postingCommentCount + 1,
                    postingComments = it.postingComments + newComment
                )
            }
            // posting 데이터에 추가
            postingUserCases.updatePosting(
                Posting(
                    postingId = _uiState.value.postingId,
                    title = _uiState.value.postingTitle,
                    content = _uiState.value.postingContent,
                    postingType = _uiState.value.postingType,
                    postingAuthorInfo = _uiState.value.postingAuthorInfo,
                    viewCount = _uiState.value.postingViewCount,
                    commentCount = _uiState.value.postingCommentCount,
                    createdAt = _uiState.value.postingCreatedAt,
                    updatedAt = _uiState.value.postingUpdatedAt,
                    comments = _uiState.value.postingComments
                )
            ).collect()
        }
    }
}