package suhyeok.yang.feature.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.common.toUserSession
import com.yang.business.usecase.auth.AuthUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userSessionUseCases: UserSessionUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Init)
    val loginState = _loginState.asStateFlow()

    private val _newUserId = MutableStateFlow("")
    val newUserId = _newUserId.asStateFlow()

    fun loginWithNaver() {
        viewModelScope.launch {
            authUseCases.loginWithNaverUseCase().collectLatest { loginResult ->
                when (loginResult) {
                    is DataResourceResult.Success -> {
                        _newUserId.value = loginResult.data.profileId

                        userSessionUseCases.checkUserRegisteredUseCase(_newUserId.value)
                            .collectLatest { registeredResult ->
                                when (registeredResult) {
                                    is DataResourceResult.Success -> {
                                        userSessionUseCases.updateUserSession(registeredResult.data.toUserSession())
                                        // 메인 화면으로 이동
                                        _loginState.value = LoginState.HasProfile
                                    }

                                    is DataResourceResult.Failure -> {
                                        // 프로필 등록 화면으로 이동
                                        _loginState.value = LoginState.FirstLogin
                                    }

                                    is DataResourceResult.Loading -> {
                                        _loginState.value = LoginState.Loading
                                    }

                                    else -> {
                                        _loginState.value = LoginState.Loading
                                    }
                                }
                            }
                    }

                    is DataResourceResult.Failure -> {
                        _loginState.value = LoginState.Failure
                    }

                    is DataResourceResult.Loading -> {
                        _loginState.value = LoginState.Loading
                    }

                    else -> {}
                }
            }
        }
    }
}

