package suhyeok.yang.shared.di

import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.chatroom.ChatRoomUseCases
import com.yang.business.usecase.hometopbanner.HomeTopBannerUseCases
import com.yang.business.usecase.message.MessageUseCases
import com.yang.business.usecase.posting.PostingUseCases
import com.yang.business.usecase.postinghistory.PostingHistoryUseCases
import com.yang.business.usecase.recruitposting.RecruitPostingUseCases
import com.yang.business.usecase.user.UserUseCases
import com.yang.business.usecase.usersession.UserSessionUseCases

interface ApplicationContainer {
    val userUseCases: UserUseCases
    val postingUseCases: PostingUseCases
    val postingHistoryUseCases: PostingHistoryUseCases
    val bandUseCases: BandUseCases
    val homeTopBannerUseCases: HomeTopBannerUseCases
    val chatRoomUseCases: ChatRoomUseCases
    val recruitPostingUseCases: RecruitPostingUseCases
    val userSessionUseCases: UserSessionUseCases
    val messageUseCases: MessageUseCases
}