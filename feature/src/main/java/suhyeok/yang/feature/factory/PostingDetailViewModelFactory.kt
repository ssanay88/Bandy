package suhyeok.yang.feature.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yang.business.usecase.posting.PostingUseCases
import com.yang.business.usecase.user.UserUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.feature.ui.posting.PostingDetailViewModel

class PostingDetailViewModelFactory(
    private val postingUseCases: PostingUseCases,
    private val userSessionUseCases: UserSessionUseCases,
    private val userUseCases: UserUseCases
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostingDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostingDetailViewModel(
                postingUserCases = postingUseCases,
                userSessionUseCases = userSessionUseCases,
                userUseCases = userUseCases
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}