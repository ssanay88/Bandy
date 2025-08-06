package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.user.UserUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.feature.ui.band.CreateBandViewModel

class CreateBandViewModelFactory(
    private val userUseCases: UserUseCases,
    private val bandUseCases: BandUseCases,
    private val userSessionUseCase: UserSessionUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateBandViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreateBandViewModel(
                userUseCases = userUseCases,
                bandUseCases = bandUseCases,
                userSessionUseCase = userSessionUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}