package suhyeok.yang.feature.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.model.User
import com.yang.business.usecase.user.UserUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import suhyeok.yang.data.mapper.toUserSession

class ProfileRegViewModel(
    private val userUseCases: UserUseCases,
    private val userSessionUseCase: UserSessionUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileRegUiState())
    val uiState = _uiState.asStateFlow()

    fun registerNewUser(newUser: User) {
        viewModelScope.launch {
            userUseCases.createUser(newUser).collectLatest { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(overallLoading = false)
                        }

                        is DataResourceResult.Failure -> {
                            it.copy(overallLoading = false)
                        }

                        is DataResourceResult.Loading -> {
                            it.copy(overallLoading = true)
                        }

                        else -> {
                            it
                        }
                    }
                }
            }
        }
    }

    fun updateUserSession(user: User) {
        viewModelScope.launch {
            userSessionUseCase.updateUserSession(user.toUserSession())
        }
    }

}