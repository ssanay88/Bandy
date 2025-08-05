package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.feature.ui.profile.ProfileViewModel

class ProfileViewModelFactory(
    private val userSessionUseCase: UserSessionUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(userSessionUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}