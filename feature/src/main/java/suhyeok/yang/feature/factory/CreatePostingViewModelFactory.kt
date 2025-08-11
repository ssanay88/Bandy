package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.posting.PostingUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.feature.ui.posting.CreatePostingViewModel

class CreatePostingViewModelFactory(
    private val postingUseCases: PostingUseCases,
    private val userSessionUseCases: UserSessionUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreatePostingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreatePostingViewModel(
                postingUseCases = postingUseCases,
                userSessionUseCases = userSessionUseCases
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}