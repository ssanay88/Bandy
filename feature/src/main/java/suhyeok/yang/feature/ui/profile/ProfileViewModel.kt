package suhyeok.yang.feature.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import suhyeok.yang.shared.common.util.toInstrument
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
            with(dataStoreRepository) {
                _uiState.value = _uiState.value.copy(
                    profileImageUrl = userProfileImage.first(),
                    userNickname = userNickname.first(),
                    userInstrument = userInstrument.first().toInstrument(),
                    userDescription = userDescription.first()
                )
            }
        }
    }

    fun isBandLeader() {
        viewModelScope.launch {
            with(dataStoreRepository) {
                if (isBand.first() && isLeader.first()) _uiState.update { it.copy(isBandLeader = true) }
            }
        }
    }

}