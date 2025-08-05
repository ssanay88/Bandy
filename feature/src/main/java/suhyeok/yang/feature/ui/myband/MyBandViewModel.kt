package suhyeok.yang.feature.ui.myband

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.model.Band
import com.yang.business.usecase.band.BandUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyBandViewModel(
    val bandUseCases: BandUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyBandUiState())
    val uiState = _uiState.asStateFlow()

    init {
        checkHasBand()
    }

    fun checkHasBand() {
        // TODO datastore을 통해 회원 정보 불러와서 넣기
        viewModelScope.launch {
            bandUseCases.readBand("band_004").onEach { result ->
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