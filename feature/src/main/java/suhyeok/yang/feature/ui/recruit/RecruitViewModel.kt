package suhyeok.yang.feature.ui.recruit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.recruitposting.RecruitPostingUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RecruitViewModel(
    val bandUseCases: BandUseCases,
    val recruitPostingUseCases: RecruitPostingUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(RecruitUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadRecruitingBandList()
        loadRecruitingMemberList()
    }

    fun loadRecruitingBandList() {
        viewModelScope.launch {
            bandUseCases.readRecruitingBand().onEach { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(
                                recruitingBandList = result.data,
                                isRecruitingBandListLoading = false
                            )
                        }

                        is DataResourceResult.Failure -> {
                            it.copy(
                                recruitingBandListErrorMessage = result.exception.message,
                                isRecruitingBandListLoading = false
                            )
                        }

                        is DataResourceResult.Loading -> {
                            it.copy(isRecruitingBandListLoading = true)
                        }

                        else -> {
                            it
                        }
                    }
                }
            }.collect()
        }
    }

    fun loadRecruitingMemberList() {
        viewModelScope.launch {
            recruitPostingUseCases.readRecruitPostingList().collectLatest { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(
                                recruitPostingList = result.data,
                                isRecruitPostingListLoading = false
                            )
                        }

                        is DataResourceResult.Failure -> {
                            it.copy(
                                recruitPostingListErrorMessage = result.exception.message,
                                isRecruitPostingListLoading = false
                            )
                        }

                        is DataResourceResult.Loading -> {
                            it.copy(isRecruitPostingListLoading = true)
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