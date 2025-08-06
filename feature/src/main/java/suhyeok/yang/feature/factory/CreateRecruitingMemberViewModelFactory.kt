package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.recruitposting.RecruitPostingUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.feature.ui.recruitmember.CreateRecruitingMemberViewModel

class CreateRecruitingMemberViewModelFactory(
    private val userSessionUseCases: UserSessionUseCases,
    private val bandUseCases: BandUseCases,
    private val recruitPostingUseCases: RecruitPostingUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateRecruitingMemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateRecruitingMemberViewModel(
                userSessionUseCases = userSessionUseCases,
                bandUseCases = bandUseCases,
                recruitPostingUseCases = recruitPostingUseCases
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}