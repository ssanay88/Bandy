package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.band.BandUseCases
import suhyeok.yang.feature.ui.band.BandDetailInfoViewModel

class BandDetailInfoViewModelFactory(
    private val bandUseCases: BandUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BandDetailInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BandDetailInfoViewModel(
                bandUseCases = bandUseCases
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}