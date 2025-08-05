package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.feature.viewmodel.SplashActivityViewModel

class SplashActivityViewModelFactory( private val userSessionUseCases: UserSessionUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashActivityViewModel(userSessionUseCases) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}