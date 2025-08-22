package suhyeok.yang.feature.ui.posting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.enums.PostingType
import com.yang.business.model.Posting
import com.yang.business.model.PostingAuthorInfo
import com.yang.business.repository.DataStoreRepository
import com.yang.business.usecase.posting.PostingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreatePostingViewModel @Inject constructor(
    private val postingUseCases: PostingUseCases,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreatePostingUiState())
    val uiState = _uiState.asStateFlow()

    private val _createPostingState = MutableStateFlow<CreatePostingState>(CreatePostingState.DummyState)
    val createPostingState = _createPostingState.asStateFlow()

    fun createPosting() {
        viewModelScope.launch {
            userSessionUseCases.getUserSession().collectLatest { userSession ->
                val newPosting = Posting(
                    postingId = "posting_${UUID.randomUUID()}",
                    title = uiState.value.postingTitle,
                    content = uiState.value.postingContent,
                    postingType = uiState.value.postingType,
                    postingAuthorInfo = PostingAuthorInfo(
                        authorId = userSession.userId,
                        authorNickName = userSession.userNickname,
                        authorProfileImageUrl = userSession.userProfileImage
                    ),
                    viewCount = 0,
                    commentCount = 0,
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                    comments = emptyList()
                )
                postingUseCases.createPosting(newPosting).collectLatest { result ->
                    when (result) {
                        is DataResourceResult.Loading -> {
                            _createPostingState.value = CreatePostingState.Uploading
                        }
                        is DataResourceResult.Success -> {
                            _createPostingState.value = CreatePostingState.Complete
                        }
                        is DataResourceResult.Failure -> {
                            _createPostingState.value = CreatePostingState.Fail(result.exception.message)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    fun selectPostingType(postingType: PostingType) {
        _uiState.update { it.copy(postingType = postingType) }
    }

    fun setPostingTitle(postingTitle: String) {
        _uiState.update { it.copy(postingTitle = postingTitle) }
    }

    fun setPostingContent(postingContent: String) {
        _uiState.update { it.copy(postingContent = postingContent) }
    }

}