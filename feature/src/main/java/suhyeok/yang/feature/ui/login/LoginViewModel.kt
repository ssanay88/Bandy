package suhyeok.yang.feature.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.model.User
import com.yang.business.usecase.usersession.UserSessionUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import suhyeok.yang.data.login.LoginManager
import suhyeok.yang.data.mapper.toUserSession

class LoginViewModel(
    private val userSessionUseCases: UserSessionUseCases
) : ViewModel() {

    init {
        observeLoginResult()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Init)
    val loginState = _loginState.asStateFlow()

    private val _newUserId = MutableStateFlow("")
    val newUserId = _newUserId.asStateFlow()


    fun observeLoginResult() {
        viewModelScope.launch {
            LoginManager.loginResult.collectLatest { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        val loginResponse = result.data
                        // 1. 토큰으로 Naver ID 받아오기
                        _newUserId.value = loginResponse.profileId

                        // 2. ID로 DB에서 등록된 유저인지 확인
                        userSessionUseCases.checkUserRegisteredUseCase(loginResponse.profileId).collectLatest { result ->
                            when (result) {
                                is DataResourceResult.Success -> {
                                    userSessionUseCases.updateUserSession(result.data.toUserSession())
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

