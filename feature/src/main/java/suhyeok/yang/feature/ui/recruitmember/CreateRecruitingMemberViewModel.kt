package suhyeok.yang.feature.ui.recruitmember

import androidx.lifecycle.ViewModel
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.recruitposting.RecruitPostingUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases

class CreateRecruitingMemberViewModel(
    val userSessionUseCases: UserSessionUseCases,
    val bandUseCases: BandUseCases,
    val recruitPostingUseCases: RecruitPostingUseCases
) : ViewModel() {
}