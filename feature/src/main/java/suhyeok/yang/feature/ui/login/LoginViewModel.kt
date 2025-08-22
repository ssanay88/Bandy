package suhyeok.yang.feature.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.yang.business.common.DataResourceResult
import com.yang.business.common.toUserSession
import com.yang.business.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Init)
    val loginState = _loginState.asStateFlow()

    private val _newUserId = MutableStateFlow("")
    val newUserId = _newUserId.asStateFlow()

    fun loginWithNaver(context: Context) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val loginResult = runCatching {
                loginWithNaverSDK(context)
            }.getOrElse {
                _loginState.value = LoginState.Failure
                return@launch
            }

            when (loginResult) {
                is DataResourceResult.Success -> {
                    loginResult.data.profile?.id?.let { profileId ->
                        checkUserRegistered(profileId)
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

    private suspend fun loginWithNaverSDK(context: Context): DataResourceResult<NidProfileResponse> =
        suspendCancellableCoroutine { continuation ->
            NaverIdLoginSDK.authenticate(context, object : OAuthLoginCallback {
                override fun onError(errorCode: Int, message: String) {
                    continuation.resume(DataResourceResult.Failure(Throwable("$errorCode $message")))
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    continuation.resume(DataResourceResult.Failure(Throwable(message)))
                }

                override fun onSuccess() {
                    NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                        override fun onError(errorCode: Int, message: String) {
                            continuation.resume(DataResourceResult.Failure(Throwable("$errorCode $message")))
                        }

                        override fun onFailure(httpStatus: Int, message: String) {
                            continuation.resume(DataResourceResult.Failure(Throwable(message)))
                        }

                        override fun onSuccess(result: NidProfileResponse) {
                            continuation.resume(DataResourceResult.Success(result))
                        }

                    })
                }
            })
        }

    private fun checkUserRegistered(profileId: String) {
        viewModelScope.launch {
            userSessionUseCases.checkUserRegisteredUseCase(profileId).collectLatest { registeredResult ->
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
    }

}

