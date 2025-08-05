package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.band.BandUseCases
import suhyeok.yang.feature.ui.band.BandInfoViewModel

class BandInfoViewModelFactory(
    private val bandUseCases: BandUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BandInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BandInfoViewModel(
                bandUseCases = bandUseCases
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}