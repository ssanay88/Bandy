package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.user.UserUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.feature.ui.profile.ProfileRegViewModel

class ProfileRegViewModelFactory(
    private val userUseCases: UserUseCases,
    private val userSessionUseCase: UserSessionUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileRegViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileRegViewModel(
                userUseCases,
                userSessionUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}