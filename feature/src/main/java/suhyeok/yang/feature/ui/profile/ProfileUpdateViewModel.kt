package suhyeok.yang.feature.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.enums.Instrument
import com.yang.business.model.Region
import com.yang.business.model.UserSession
import com.yang.business.usecase.user.UserUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileUpdateViewModel @Inject constructor(
    private val userSessionUseCases: UserSessionUseCases,
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUpdateUiState())
    val uiState = _uiState.asStateFlow()

    private lateinit var initialUserSession: UserSession

    init {
        loadMyProfileInfo()
    }

    fun loadMyProfileInfo() {
        viewModelScope.launch {
            userSessionUseCases.getUserSession().collectLatest { userSession ->
                _uiState.update {
                    with(userSession) {
                        initialUserSession = userSession.copy()

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
            userSessionUseCases.updateUserSession(
                initialUserSession.copy(
                    userProfileImage = _uiState.value.myProfileImageUrl,
                    userNickname = _uiState.value.myProfileNickname,
                    userInstrument = _uiState.value.myProfileInstrument,
                    userRegion = Region(
                        sido = _uiState.value.myProfileSido,
                        sigungu = _uiState.value.myProfileSigungu
                    ),
                    userDescription = _uiState.value.myProfileIntroduce
                )
            )
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