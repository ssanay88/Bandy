package suhyeok.yang.feature.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.usecase.usersession.UserSessionUseCases
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SplashActivityViewModel(
    private val userSessionUseCases: UserSessionUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        checkUserLogin()
    }

    private fun checkUserLogin() {
        viewModelScope.launch {
            delay(1500L)

            userSessionUseCases.getUserSession().collectLatest { userSession ->
                when {
                    userSession.isLogged -> _uiState.value = SplashUiState.NavigateToMainActivity
                    !userSession.isLogged -> _uiState.value = SplashUiState.NavigateToLoginActivity
                }
            }
        }
    }
}

sealed class SplashUiState {
    object Loading: SplashUiState()
    object NavigateToMainActivity: SplashUiState()
    object NavigateToLoginActivity: SplashUiState()
}