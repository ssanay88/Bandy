package suhyeok.yang.feature.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.model.User
import com.yang.business.repository.DataStoreRepository
import com.yang.business.usecase.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import suhyeok.yang.shared.common.util.toStr
import javax.inject.Inject

@HiltViewModel
class ProfileRegViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val dataStoreRepository: DataStoreRepository,
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
            dataStoreRepository.apply {
                setUserId(user.userId)
                setUserName(user.userName)
                setUserNickname(user.nickName)
                setUserProfileImage(user.profileImageUrl)
                setUserInstrument(user.instrument.toStr())
                setUserDescription(user.userDescription)
                setUserSido(user.region.sido)
                setUserSigungu(user.region.sigungu)
                setUserGender(user.gender.toStr())
                setUserSkillLevel(user.skillLevel.toStr())
                setUserBirthDate(user.birthDate.toString())
            }
        }
    }

    fun setLoggedIn() {
        viewModelScope.launch {
            dataStoreRepository.setIsLoggedIn(true)
        }
    }

}