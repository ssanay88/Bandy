package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.recruitposting.RecruitPostingUseCases
import suhyeok.yang.feature.ui.recruit.RecruitViewModel

class RecruitViewModelFactory(
    private val bandUseCases: BandUseCases,
    private val recruitPostingUseCases: RecruitPostingUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecruitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecruitViewModel(
                bandUseCases = bandUseCases,
                recruitPostingUseCases = recruitPostingUseCases
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}