package suhyeok.yang.feature.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initMyProfile()
    }

    fun initMyProfile() {
        viewModelScope.launch {
            userSessionUseCases.getUserSession().collectLatest { userSession ->
                _uiState.value = _uiState.value.copy(
                    profileImageUrl = userSession.userProfileImage,
                    userNickname = userSession.userNickname,
                    userInstrument = userSession.userInstrument,
                    userDescription = userSession.userDescription
                )
            }
        }
    }

}