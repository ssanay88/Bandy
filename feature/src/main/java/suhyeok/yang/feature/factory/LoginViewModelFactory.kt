package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.feature.ui.login.LoginViewModel

class LoginViewModelFactory(
    private val userSessionUseCases: UserSessionUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(userSessionUseCases) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}