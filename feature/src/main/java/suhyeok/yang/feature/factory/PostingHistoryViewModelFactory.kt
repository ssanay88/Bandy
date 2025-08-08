package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.postinghistory.PostingHistoryUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.feature.ui.profile.PostingHistoryViewModel

class PostingHistoryViewModelFactory(
    private val userSessionUseCases: UserSessionUseCases,
    private val postingHistoryUseCases: PostingHistoryUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostingHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostingHistoryViewModel(
                userSessionUseCases = userSessionUseCases,
                postingHistoryUseCases = postingHistoryUseCases
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}