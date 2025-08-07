package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.user.UserUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.feature.ui.profile.ProfileUpdateViewModel

class ProfileUpdateViewModelFactory(
    private val userSessionUseCase: UserSessionUseCases,
    private val userUseCases: UserUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileUpdateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileUpdateViewModel(
                userSessionUseCase,
                userUseCases
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}