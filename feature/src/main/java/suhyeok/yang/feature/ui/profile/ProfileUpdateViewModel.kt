package suhyeok.yang.feature.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.enums.Instrument
import com.yang.business.model.UserSession
import com.yang.business.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import suhyeok.yang.shared.common.util.toInstrument
import suhyeok.yang.shared.common.util.toStr
import javax.inject.Inject

@HiltViewModel
class ProfileUpdateViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUpdateUiState())
    val uiState = _uiState.asStateFlow()

    private lateinit var initialUserSession: UserSession

    fun loadMyProfileInfo() {
        viewModelScope.launch {
            _uiState.update { it.copy(overallLoading = true) }

            with(dataStoreRepository) {
                _uiState.update { uiState ->
                    uiState.copy(
                        overallLoading = false,
                        myProfileImageUrl = userProfileImage.first(),
                        myProfileNickname = userNickname.first(),
                        myProfileInstrument = userInstrument.first().toInstrument(),
                        myProfileSido = userSido.first(),
                        myProfileSigungu = userSigungu.first(),
                        myProfileIntroduce = userDescription.first()
                    )
                }
            }
        }
    }

    fun resetProfileChanges() {
        _uiState.update {
            with(initialUserSession) {
                it.copy(
                    myProfileImageUrl = userProfileImage,
                    myProfileNickname = userNickname,
                    myProfileInstrument = userInstrument,
                    myProfileSido = userRegion.sido,
                    myProfileSigungu = userRegion.sigungu,
                    myProfileIntroduce = userDescription
                )
            }
        }
    }

    fun updateMyProfile() {
        viewModelScope.launch {
            // Usersession 변경
            dataStoreRepository.apply {
                setUserProfileImage(_uiState.value.myProfileImageUrl)
                setUserNickname(_uiState.value.myProfileNickname)
                setUserInstrument(_uiState.value.myProfileInstrument.toStr())
                setUserSido(_uiState.value.myProfileSido)
                setUserSigungu(_uiState.value.myProfileSigungu)
                setUserDescription(_uiState.value.myProfileIntroduce)
            }
        }
    }

    fun validateUpdatedProfileInfo(): ProfileUpdateValidationResult =
        when {
            _uiState.value.myProfileInstrument == Instrument.NOTHING -> ProfileUpdateValidationResult.InstrumentUnselected
            _uiState.value.myProfileNickname.isEmpty() -> ProfileUpdateValidationResult.NicknameEmpty
            else -> ProfileUpdateValidationResult.Success
        }

    fun updateMyProfileImageUrl(imageUrl: String) {
        _uiState.update {
            it.copy(myProfileImageUrl = imageUrl)
        }
    }

    fun updateMyProfileNickname(nickname: String) {
        _uiState.update {
            it.copy(myProfileNickname = nickname)
        }
    }

    fun updateMyProfileInstrument(instrument: Instrument) {
        _uiState.update {
            it.copy(myProfileInstrument = instrument)
        }
    }

    fun updateMyProfileSido(sido: String) {
        _uiState.update {
            it.copy(myProfileSido = sido)
        }
    }

    fun updateMyProfileSigungu(sigungu: String) {
        _uiState.update {
            it.copy(myProfileSigungu = sigungu)
        }
    }

    fun updateMyProfileIntroduce(introduce: String) {
        _uiState.update {
            it.copy(myProfileIntroduce = introduce)
        }
    }


}