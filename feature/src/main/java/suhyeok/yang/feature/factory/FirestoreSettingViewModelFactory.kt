package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.chatroom.ChatRoomUseCases
import com.yang.business.usecase.posting.PostingUseCases
import com.yang.business.usecase.recruitposting.RecruitPostingUseCases
import com.yang.business.usecase.user.UserUseCases
import suhyeok.yang.feature.viewmodel.FirestoreSettingViewModel

class FirestoreSettingViewModelFactory(
    private val userUseCases: UserUseCases,
    private val bandUseCases: BandUseCases,
    private val postingUseCases: PostingUseCases,
    private val recruitPostingUseCases: RecruitPostingUseCases,
    private val chatRoomUseCases: ChatRoomUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FirestoreSettingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FirestoreSettingViewModel(
                userUseCases = userUseCases,
                bandUseCases = bandUseCases,
                postingUseCases = postingUseCases,
                recruitPostingUseCases = recruitPostingUseCases,
                chatRoomUseCases = chatRoomUseCases
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}