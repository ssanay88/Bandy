package suhyeok.yang.feature.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.hometopbanner.HomeTopBannerUseCases
import com.yang.business.usecase.posting.PostingUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeTopBannerUserCases: HomeTopBannerUseCases,
    private val bandUseCases: BandUseCases,
    private val postingUseCases: PostingUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadHomeScreenData()
    }

    fun loadHomeScreenData() {
        viewModelScope.launch {
            val topBannerFlow = homeTopBannerUserCases.readHomeTopBanner().onEach { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(
                                topBannerList = result.data,
                                isTopBannerLoading = false
                            )
                        }

                        is DataResourceResult.Failure -> {
                            it.copy(
                                topBannerErrorMessage = result.exception.message,
                                isTopBannerLoading = false
                            )
                        }

                        is DataResourceResult.Loading -> {
                            it.copy(isTopBannerLoading = true)
                        }

                        else -> {
                            it
                        }
                    }
                }
            }

            val popularBandFlow = bandUseCases.readPopularBand().onEach { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(
                                popularBandList = result.data,
                                isPopularBandLoading = false
                            )
                        }

                        is DataResourceResult.Failure -> {
                            it.copy(
                                popularBandErrorMessage = result.exception.message,
                                isPopularBandLoading = false
                            )
                        }

                        is DataResourceResult.Loading -> {
                            it.copy(isPopularBandLoading = true)
                        }

                        else -> {
                            it
                        }
                    }
                }
            }

            val postingFlow = postingUseCases.readPosting().onEach { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(
                                postingList = result.data,
                                isPostingLoading = false
                            )
                        }

                        is DataResourceResult.Failure -> {
                            it.copy(
                                postingErrorMessage = result.exception.message,
                                isPostingLoading = false
                            )
                        }

                        is DataResourceResult.Loading -> {
                            it.copy(isPostingLoading = true)
                        }

                        else -> {
                            it
                        }
                    }
                }
            }

            combine(
                topBannerFlow,
                popularBandFlow,
                postingFlow
            ) { topBannerResult, popularBandResult, postingResult ->
                val isAnyLoading = topBannerResult is DataResourceResult.Loading ||
                        popularBandResult is DataResourceResult.Loading ||
                        postingResult is DataResourceResult.Loading

                _uiState.update {
                    it.copy(overallLoading = isAnyLoading)
                }

            }.collect()

        }
    }
}