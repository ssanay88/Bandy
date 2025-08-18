package suhyeok.yang.bandy.di

import com.yang.business.repository.BandRepository
import com.yang.business.repository.ChatRoomRepository
import com.yang.business.repository.HomeTopBannerRepository
import com.yang.business.repository.PostingHistoryRepository
import com.yang.business.repository.PostingRepository
import com.yang.business.repository.RecruitPostingRepository
import com.yang.business.repository.UserRepository
import com.yang.business.repository.UserSessionRepository
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.band.CreateBandUseCase
import com.yang.business.usecase.band.DeleteBandUseCase
import com.yang.business.usecase.band.ReadBandListUseCase
import com.yang.business.usecase.band.ReadBandUseCase
import com.yang.business.usecase.band.ReadPopularBandUseCase
import com.yang.business.usecase.band.ReadRecruitingBandUseCase
import com.yang.business.usecase.band.UpdateBandUseCase
import com.yang.business.usecase.chatroom.ChatRoomUseCases
import com.yang.business.usecase.chatroom.CreateChatRoomUseCase
import com.yang.business.usecase.chatroom.DeleteChatRoomUseCase
import com.yang.business.usecase.chatroom.ReadChatRoomUseCase
import com.yang.business.usecase.chatroom.UpdateChatRoomUseCase
import com.yang.business.usecase.hometopbanner.HomeTopBannerUseCases
import com.yang.business.usecase.hometopbanner.ReadHomeTopBannerUseCase
import com.yang.business.usecase.message.DeleteMessageUseCase
import com.yang.business.usecase.message.GetChatParticipantsUseCase
import com.yang.business.usecase.message.GetUnreadMessageCountUseCase
import com.yang.business.usecase.message.GetUserInfoUseCase
import com.yang.business.usecase.message.MarkMessageAsReadUseCase
import com.yang.business.usecase.message.MessageUseCases
import com.yang.business.usecase.message.ObserveMessageUseCase
import com.yang.business.usecase.message.SendMessageUseCase
import com.yang.business.usecase.posting.CreatePostingUseCase
import com.yang.business.usecase.posting.DeletePostingUseCase
import com.yang.business.usecase.posting.PostingUseCases
import com.yang.business.usecase.posting.ReadPostingDetailUseCase
import com.yang.business.usecase.posting.ReadPostingUseCase
import com.yang.business.usecase.posting.UpdatePostingUseCase
import com.yang.business.usecase.postinghistory.PostingHistoryUseCases
import com.yang.business.usecase.postinghistory.ReadCommentedPostingUseCase
import com.yang.business.usecase.postinghistory.ReadMyPostingUseCase
import com.yang.business.usecase.recruitposting.CreateRecruitPostingUseCase
import com.yang.business.usecase.recruitposting.DeleteRecruitPostingUseCase
import com.yang.business.usecase.recruitposting.ReadRecruitPostingListUseCase
import com.yang.business.usecase.recruitposting.ReadRecruitPostingUseCase
import com.yang.business.usecase.recruitposting.RecruitPostingUseCases
import com.yang.business.usecase.recruitposting.UpdateRecruitPostingUseCase
import com.yang.business.usecase.user.CreateUserUseCase
import com.yang.business.usecase.user.DeleteUserUseCase
import com.yang.business.usecase.user.ReadUserUseCase
import com.yang.business.usecase.user.SearchUserByNicknameUseCase
import com.yang.business.usecase.user.UpdateUserUseCase
import com.yang.business.usecase.user.UserUseCases
import com.yang.business.usecase.usersession.CheckUserRegisteredUseCase
import com.yang.business.usecase.usersession.ClearUserSessionUseCase
import com.yang.business.usecase.usersession.GetUserSessionUseCase
import com.yang.business.usecase.usersession.UpdateUserSessionUseCase
import com.yang.business.usecase.usersession.UserSessionUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideUserUseCases(userRepository: UserRepository): UserUseCases {
        return UserUseCases(
            readUser = ReadUserUseCase(userRepository),
            createUser = CreateUserUseCase(userRepository),
            updateUser = UpdateUserUseCase(userRepository),
            deleteUser = DeleteUserUseCase(userRepository),
            searchUserByNickname = SearchUserByNicknameUseCase(userRepository)
        )
    }

    @Provides
    @Singleton
    fun provideBandUseCases(bandRepository: BandRepository): BandUseCases {
        return BandUseCases(
            readBand = ReadBandUseCase(bandRepository),
            createBand = CreateBandUseCase(bandRepository),
            updateBand = UpdateBandUseCase(bandRepository),
            deleteBand = DeleteBandUseCase(bandRepository),
            readBandList = ReadBandListUseCase(bandRepository),
            readPopularBand = ReadPopularBandUseCase(bandRepository),
            readRecruitingBand = ReadRecruitingBandUseCase(bandRepository)
        )
    }

    @Provides
    @Singleton
    fun provideHomeTopBannerUseCases(homeTopBannerRepository: HomeTopBannerRepository): HomeTopBannerUseCases {
        return HomeTopBannerUseCases(
            readHomeTopBanner = ReadHomeTopBannerUseCase(homeTopBannerRepository)
            )
    }

    @Provides
    @Singleton
    fun provideChatroomUseCases(chatRoomRepository: ChatRoomRepository): ChatRoomUseCases {
        return ChatRoomUseCases(
            createChatRoomUseCase = CreateChatRoomUseCase(chatRoomRepository),
            readChatRoomUseCase = ReadChatRoomUseCase(chatRoomRepository),
            updateChatRoomUseCase = UpdateChatRoomUseCase(chatRoomRepository),
            deleteChatRoomUseCase = DeleteChatRoomUseCase(chatRoomRepository)
        )
    }

    @Provides
    @Singleton
    fun provideMessageUseCases(chatRoomRepository: ChatRoomRepository, useRepository: UserRepository): MessageUseCases {
        return MessageUseCases(
            sendMessageUseCase = SendMessageUseCase(chatRoomRepository),
            observeMessageUseCase = ObserveMessageUseCase(chatRoomRepository),
            markMessageAsReadUseCase = MarkMessageAsReadUseCase(chatRoomRepository),
            getUnreadMessageCountUserCase = GetUnreadMessageCountUseCase(chatRoomRepository),
            getChatParticipantsUseCase = GetChatParticipantsUseCase(chatRoomRepository),
            getUserInfoUseCase = GetUserInfoUseCase(useRepository),
            deleteMessageUseCase = DeleteMessageUseCase(chatRoomRepository)
        )
    }

    @Provides
    @Singleton
    fun providePostingUseCases(postingRepository: PostingRepository): PostingUseCases {
        return PostingUseCases(
            createPosting = CreatePostingUseCase(postingRepository),
            readPosting = ReadPostingUseCase(postingRepository),
            readPostingDetail = ReadPostingDetailUseCase(postingRepository),
            updatePosting = UpdatePostingUseCase(postingRepository),
            deletePosting = DeletePostingUseCase(postingRepository)
        )
    }

    @Provides
    @Singleton
    fun providePostingHistoryUseCases(postingHistoryRepository: PostingHistoryRepository): PostingHistoryUseCases {
        return PostingHistoryUseCases(
            readMyPosting = ReadMyPostingUseCase(postingHistoryRepository),
            readCommentedPosting = ReadCommentedPostingUseCase(postingHistoryRepository)
        )
    }

    @Provides
    @Singleton
    fun provideRecruitPostingUseCases(recruitPostingRepository: RecruitPostingRepository): RecruitPostingUseCases {
        return RecruitPostingUseCases(
            createRecruitPosting = CreateRecruitPostingUseCase(recruitPostingRepository),
            readRecruitPosting = ReadRecruitPostingUseCase(recruitPostingRepository),
            readRecruitPostingList = ReadRecruitPostingListUseCase(recruitPostingRepository),
            updateRecruitPosting = UpdateRecruitPostingUseCase(recruitPostingRepository),
            deleteRecruitPosting = DeleteRecruitPostingUseCase(recruitPostingRepository)
        )
    }

    @Provides
    @Singleton
    fun provideUserSessionUseCases(userSessionRepository: UserSessionRepository, userRepository: UserRepository): UserSessionUseCases {
        return UserSessionUseCases(
            getUserSession = GetUserSessionUseCase(userSessionRepository),
            checkUserRegisteredUseCase = CheckUserRegisteredUseCase(userRepository),
            updateUserSession = UpdateUserSessionUseCase(userSessionRepository),
            clearUserSession = ClearUserSessionUseCase(userSessionRepository)
        )
    }
}