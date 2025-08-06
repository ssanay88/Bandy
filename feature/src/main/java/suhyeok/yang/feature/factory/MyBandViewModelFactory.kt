package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.feature.ui.myband.MyBandViewModel

class MyBandViewModelFactory(
    private val bandUseCases: BandUseCases,
    private val userSessionUseCases: UserSessionUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyBandViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyBandViewModel(
                bandUseCases = bandUseCases,
                userSessionUseCase = userSessionUseCases
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}