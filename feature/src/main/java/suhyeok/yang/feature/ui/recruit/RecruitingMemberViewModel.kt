package suhyeok.yang.feature.ui.recruit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.repository.DataStoreRepository
import com.yang.business.usecase.chatroom.ChatRoomUseCases
import com.yang.business.usecase.recruitposting.RecruitPostingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import suhyeok.yang.feature.common.ChatRoomManager
import javax.inject.Inject

@HiltViewModel
class RecruitingMemberViewModel @Inject constructor(
    val recruitPostingUseCases: RecruitPostingUseCases,
    val chatRoomUseCases: ChatRoomUseCases,
    val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    @Inject lateinit var chatRoomManager: ChatRoomManager

    private val _uiState = MutableStateFlow(RecruitingMemberUiState())
    val uiState = _uiState.asStateFlow()

    fun loadRecruitMemberPosting(recruitPostingId: String) {
        viewModelScope.launch {
            recruitPostingUseCases.readRecruitPosting(recruitPostingId).collectLatest { result ->
                _uiState.update {
                    when(result) {
                        is DataResourceResult.Success -> {
                            with(result.data) {
                                it.copy(
                                    overallLoading = false,

                                    title = title,
                                    content = content,
                                    postedBandId = postedBandId,
                                    postedBandName = postedBandName,
                                    postedBandImageUrl = postedBandImageUrl,
                                    authorId = authorId,
                                    targetAgeGroups = targetAgeGroups,
                                    targetGender = targetGender,
                                    targetRegion = targetRegion,
                                    targetSkillLevel = targetSkillLevel,
                                    targetInstrument = targetInstrument,
                                    recruitingStatus = recruitingStatus,
                                    createdAt = createdAt,
                                    updatedAt = updatedAt,
                                    viewCount = viewCount,
                                    commentCount = commentCount
                                )
                            }
                        }

                        is DataResourceResult.Failure -> {
                            it.copy(
                                overallLoading = false
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

    fun chatWithRecruitingBandLeader(chatRoomTitle: String, chatRoomImageUrl: String, bandLeaderId: String) {
        viewModelScope.launch {
            val newChatRoom = chatRoomManager.createNewChatRoom(
                chatRoomName = chatRoomTitle,
                roomImageUrl = chatRoomImageUrl,
                participants = listOf()
            )

            chatRoomUseCases.createChatRoomUseCase(newChatRoom).collectLatest {

            }
        }
    }
}