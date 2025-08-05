package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.hometopbanner.HomeTopBannerUseCases
import com.yang.business.usecase.posting.PostingUseCases
import suhyeok.yang.feature.ui.home.HomeViewModel

class HomeViewModelFactory(
    private val topBannerUseCases: HomeTopBannerUseCases,
    private val bandUseCases: BandUseCases,
    private val postingUseCases: PostingUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(
                homeTopBannerUserCases = topBannerUseCases,
                bandUseCases = bandUseCases,
                postingUseCases = postingUseCases
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}