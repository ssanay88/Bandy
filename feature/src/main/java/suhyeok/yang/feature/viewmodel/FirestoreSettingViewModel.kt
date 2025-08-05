package suhyeok.yang.feature.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.model.Band
import com.yang.business.model.ChatRoom
import com.yang.business.model.Posting
import com.yang.business.model.RecruitPosting
import com.yang.business.model.User
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.chatroom.ChatRoomUseCases
import com.yang.business.usecase.posting.PostingUseCases
import com.yang.business.usecase.recruitposting.RecruitPostingUseCases
import com.yang.business.usecase.user.UserUseCases
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import suhyeok.yang.feature.MockData

class FirestoreSettingViewModel(
    private val userUseCases: UserUseCases,
    private val bandUseCases: BandUseCases,
    private val postingUseCases: PostingUseCases,
    private val recruitPostingUseCases: RecruitPostingUseCases,
    private val chatRoomUseCases: ChatRoomUseCases
) : ViewModel() {

    // UserData Setting
    fun uploadUserData(userList: List<User>) {
        viewModelScope.launch {
            userList.forEach { user ->
                userUseCases.createUser(user).collectLatest {
                }
            }
        }
    }

    fun updateUserData(userList: List<User>) {
        viewModelScope.launch {
            userList.forEach { user ->
                userUseCases.updateUser(user).collectLatest {
                }
            }
        }
    }

    // Firestore에 밴드 데이터 올리기
    fun uploadBandData(bandList: List<Band>) {
        viewModelScope.launch {
            bandList.forEach { band ->
                bandUseCases.createBand(band).collectLatest {
                }
            }
        }
    }

    fun updateBandData(bandList: List<Band>) {
        viewModelScope.launch {
            bandList.forEach { band ->
                bandUseCases.updateBand(band).collectLatest {
                }
            }
        }
    }

    // Firestore에 포스팅 데이터 올리기
    fun uploadPosting(postingList: List<Posting>) {
        viewModelScope.launch {
            postingList.forEach { posting ->
                postingUseCases.createPosting(posting).collectLatest {
                }
            }
        }
    }

    fun updatePosting(postingList: List<Posting>) {
        viewModelScope.launch {
            postingList.forEach { posting ->
                postingUseCases.updatePosting(posting).collectLatest {
                }
            }
        }
    }

    // Firestore에 모집 공고 포스팅 데이터 올리기
    fun uploadRecruitPosting(recruitPostingList: List<RecruitPosting>) {
        viewModelScope.launch {
            recruitPostingList.forEach { recruitPosting ->
                recruitPostingUseCases.createRecruitPosting(recruitPosting).collectLatest {

                }
            }
        }
    }

    // Firestore에 채팅 목록 올리기
    fun uploadChatRoom(chatRoomList: List<ChatRoom>) {
        viewModelScope.launch {
            chatRoomList.forEach { chatRoom ->
                chatRoomUseCases.createChatRoomUseCase(chatRoom).collectLatest {
                }
            }

        }
    }

    fun updateChatRoom(chatRoomList: List<ChatRoom>) {
        viewModelScope.launch {
            chatRoomList.forEach { chatRoom ->
                chatRoomUseCases.updateChatRoomUseCase(chatRoom).collectLatest {
                }
            }
        }
    }

}