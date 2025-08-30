package suhyeok.yang.feature.ui.myband

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.repository.DataStoreRepository
import com.yang.business.usecase.band.BandUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBandViewModel @Inject constructor(
    private val bandUseCases: BandUseCases,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyBandUiState())
    val uiState = _uiState.asStateFlow()

    fun loadMyBandData() {
        viewModelScope.launch {
            val myBandId = dataStoreRepository.bandId.first()

            when {
                myBandId.isEmpty() -> {
                    _uiState.update {
                        it.copy(
                            hasBand = false,
                            isMyBandLoading = false
                        )
                    }
                }

                else -> {
                    bandUseCases.readBand(myBandId).collectLatest { result ->
                        _uiState.update {
                            when (result) {
                                is DataResourceResult.Success -> {
                                    it.copy(
                                        myBand = result.data,
                                        hasBand = true,
                                        isMyBandLoading = false
                                    )
                                }

                                is DataResourceResult.Failure -> {
                                    it.copy(
                                        myBandErrorMessage = result.exception.message,
                                        hasBand = true,
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
}