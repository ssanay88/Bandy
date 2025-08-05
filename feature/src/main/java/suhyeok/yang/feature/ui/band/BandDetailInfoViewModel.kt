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

class BandDetailInfoViewModel(
    val bandUseCases: BandUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(BandDetailInfoUiState())
    val uiState = _uiState.asStateFlow()

    fun loadMyBand(bandId: String = "band_003") {
        viewModelScope.launch {
            bandUseCases.readBand(bandId).onEach { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(
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
            }.collect()
        }
    }
}