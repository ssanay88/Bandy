package suhyeok.yang.feature.ui.myband

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.model.Band
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyBandViewModel(
    val bandUseCases: BandUseCases,
    val userSessionUseCase: UserSessionUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyBandUiState())
    val uiState = _uiState.asStateFlow()

    init {
        checkHasBand()
    }

    fun checkHasBand() {
        viewModelScope.launch {
            userSessionUseCase.getUserSession().collectLatest { userSession ->
                bandUseCases.readBand(userSession.bandId).collectLatest { result ->
                    _uiState.update {
                        when (result) {
                            is DataResourceResult.Success -> {
                                it.copy(
                                    hasBand = true,
                                    myBand = result.data,
                                    isMyBandLoading = false
                                )
                            }

                            is DataResourceResult.Failure -> {
                                it.copy(
                                    myBandErrorMessage = result.exception.message,
                                    isMyBandLoading = false
                                )
                            }

                            is DataResourceResult.Loading -> {
                                it.copy(isMyBandLoading = true)
                            }

                            else -> {
                                it
                            }

                        }
                    }
                }
            }
        }
    }
}