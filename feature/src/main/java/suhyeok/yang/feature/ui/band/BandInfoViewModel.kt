package suhyeok.yang.feature.ui.band

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.usecase.band.BandUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BandInfoViewModel(
    val bandUseCases: BandUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(BandInfoUiState())
    val uiState = _uiState.asStateFlow()

    fun loadMyBand(bandId: String) {
        viewModelScope.launch {
            bandUseCases.readBand(bandId).onEach { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(
                                band = result.data,
                                isBandLoading = false,
                                isBandLoaded = true
                            )
                        }

                        is DataResourceResult.Failure -> {
                            it.copy(
                                bandErrorMessage = result.exception.message,
                                isBandLoading = false,
                                isBandLoaded = false
                            )
                        }

                        is DataResourceResult.Loading -> {
                            it.copy(isBandLoading = true, isBandLoaded = false)
                        }

                        else -> {
                            it
                        }
                    }
                }
            }.collect()
        }
    }
}