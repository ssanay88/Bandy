package suhyeok.yang.feature.ui.band

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.model.Band
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.user.UserUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateBandViewModel(
    private val userUseCases: UserUseCases,
    private val bandUseCases: BandUseCases,
    private val userSessionUseCase: UserSessionUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateBandUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            userSessionUseCase.getUserSession().collectLatest { userSession ->
                _uiState.update {
                    it.copy(bandLeaderId = userSession.userId)
                }
            }
        }
    }

    fun searchByNickname(nickname: String) {
        viewModelScope.launch {
            userUseCases.searchUserByNickname(nickname).collectLatest { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Loading -> it.copy(overallLoading = true)
                        is DataResourceResult.Success -> it.copy(overallLoading = false, matchedUsers = result.data)
                        else -> { it }
                    }
                }
            }
        }
    }

    fun registerNewBand(newBand: Band) {
        viewModelScope.launch {
            bandUseCases.createBand(newBand).collectLatest { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Loading -> it.copy(overallLoading = true)
                        is DataResourceResult.Success -> it.copy(overallLoading = false)
                        else -> { it }
                    }
                }
            }
        }
    }

    fun updateUserWithBand(band: Band) {
        viewModelScope.launch {
            userSessionUseCase.getUserSession().firstOrNull()?.let { userSession ->
                userSessionUseCase.updateUserSession(userSession.copy(isBand = true, bandId = band.bandId))

                userUseCases.readUser(userSession.userId).onEach { result ->
                    when (result) {
                        is DataResourceResult.Success -> {
                            userUseCases.updateUser(result.data.copy(bandId = band.bandId, isLeader = true)).collect()
                        }
                        else -> {}
                    }
                }.collect()
            }
        }
    }
}