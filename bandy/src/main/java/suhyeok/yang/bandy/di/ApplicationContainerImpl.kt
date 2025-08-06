package suhyeok.yang.bandy.di

import android.content.Context
import com.yang.business.repository.BandRepository
import com.yang.business.repository.ChatRoomRepository
import com.yang.business.repository.HomeTopBannerRepository
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
import com.yang.business.usecase.recruitposting.CreateRecruitPostingUseCase
import com.yang.business.usecase.recruitposting.DeleteRecruitPostingUseCase
import com.yang.business.usecase.recruitposting.ReadRecruitPostingListUseCase
import com.yang.business.usecase.recruitposting.ReadRecruitPostingUseCase
import com.yang.business.usecase.recruitposting.RecruitPostingUseCases
import com.yang.business.usecase.recruitposting.UpdateRecruitPostingUseCase
import com.yang.business.usecase.user.DeleteUserUseCase
import com.yang.business.usecase.user.ReadUserUseCase
import com.yang.business.usecase.user.CreateUserUseCase
import com.yang.business.usecase.user.SearchUserByNicknameUseCase
import com.yang.business.usecase.user.UpdateUserUseCase
import com.yang.business.usecase.user.UserUseCases
import com.yang.business.usecase.usersession.CheckUserRegisteredUseCase
import com.yang.business.usecase.usersession.ClearUserSessionUseCase
import com.yang.business.usecase.usersession.GetUserSessionUseCase
import com.yang.business.usecase.usersession.UpdateUserSessionUseCase
import com.yang.business.usecase.usersession.UserSessionUseCases
import suhyeok.yang.data.datasource.BandDataSource
import suhyeok.yang.data.datasource.ChatRoomDataSource
import suhyeok.yang.data.datasource.HomeTopBannerDataSource
import suhyeok.yang.data.datasource.PostingDataSource
import suhyeok.yang.data.datasource.RecruitPostingDataSource
import suhyeok.yang.data.datasource.UserDataSource
import suhyeok.yang.data.datasource.UserSessionDataSource
import suhyeok.yang.data.local.datastore.UserSessionDataSourceImpl
import suhyeok.yang.data.remote.firebase.FirestoreBandDataSourceImpl
import suhyeok.yang.data.remote.firebase.FirestoreChatRoomDataSourceImpl
import suhyeok.yang.data.remote.firebase.FirestoreHomeTopBannerDataSourceImpl
import suhyeok.yang.data.remote.firebase.FirestorePostingDataSourceImpl
import suhyeok.yang.data.remote.firebase.FirestoreRecruitPostingDataSourceImpl
import suhyeok.yang.data.remote.firebase.FirestoreUserDataSourceImpl
import suhyeok.yang.data.repository.DatastoreUserSessionRepositoryImpl
import suhyeok.yang.data.repository.FirestoreBandRepositoryImpl
import suhyeok.yang.data.repository.FirestoreChatRoomRepositoryImpl
import suhyeok.yang.data.repository.FirestoreHomeTopBannerRepositoryImpl
import suhyeok.yang.data.repository.FirestorePostingRepositoryImpl
import suhyeok.yang.data.repository.FirestoreRecruitPostingRepositoryImpl
import suhyeok.yang.data.repository.FirestoreUserRepositoryImpl
import suhyeok.yang.shared.di.ApplicationContainer

class ApplicationContainerImpl(
    context: Context
): ApplicationContainer {

    private val remoteUserDataSource: UserDataSource = FirestoreUserDataSourceImpl()
    private val userRepository: UserRepository = FirestoreUserRepositoryImpl(remoteUserDataSource)

    private val remotePostingDataSource: PostingDataSource = FirestorePostingDataSourceImpl()
    private val postingRepository: PostingRepository = FirestorePostingRepositoryImpl(remotePostingDataSource)

    private val remoteBandDataSource: BandDataSource = FirestoreBandDataSourceImpl()
    private val bandRepository: BandRepository = FirestoreBandRepositoryImpl(remoteBandDataSource)

    private val remoteHomeTopBannerDataSource: HomeTopBannerDataSource = FirestoreHomeTopBannerDataSourceImpl()
    private val homeTopBannerRepository: HomeTopBannerRepository = FirestoreHomeTopBannerRepositoryImpl(remoteHomeTopBannerDataSource)

    private val remoteChatRoomDataSource: ChatRoomDataSource = FirestoreChatRoomDataSourceImpl()
    private val chatRoomRepository: ChatRoomRepository = FirestoreChatRoomRepositoryImpl(remoteChatRoomDataSource)

    private val remoteRecruitPostingDataSource: RecruitPostingDataSource = FirestoreRecruitPostingDataSourceImpl()
    private val recruitPostingRepository: RecruitPostingRepository = FirestoreRecruitPostingRepositoryImpl(remoteRecruitPostingDataSource)

    private val localUserSessionDataSource: UserSessionDataSource = UserSessionDataSourceImpl(context)
    private val userSessionRepository: UserSessionRepository = DatastoreUserSessionRepositoryImpl(localUserSessionDataSource)


    override val userUseCases = UserUseCases(
        readUser = ReadUserUseCase(userRepository),
        createUser = CreateUserUseCase(userRepository),
        updateUser = UpdateUserUseCase(userRepository),
        deleteUser = DeleteUserUseCase(userRepository),
        searchUserByNickname = SearchUserByNicknameUseCase(userRepository)
    )

    override val postingUseCases = PostingUseCases(
        createPosting = CreatePostingUseCase(postingRepository),
        readPosting = ReadPostingUseCase(postingRepository),
        readPostingDetail = ReadPostingDetailUseCase(postingRepository),
        updatePosting = UpdatePostingUseCase(postingRepository),
        deletePosting = DeletePostingUseCase(postingRepository)
    )

    override val bandUseCases = BandUseCases(
        createBand = CreateBandUseCase(bandRepository),
        readBand = ReadBandUseCase(bandRepository),
        readPopularBand = ReadPopularBandUseCase(bandRepository),
        readBandList = ReadBandListUseCase(bandRepository),
        readRecruitingBand = ReadRecruitingBandUseCase(bandRepository),
        updateBand = UpdateBandUseCase(bandRepository),
        deleteBand = DeleteBandUseCase(bandRepository)
    )

    override val homeTopBannerUseCases = HomeTopBannerUseCases(
        readHomeTopBanner = ReadHomeTopBannerUseCase(homeTopBannerRepository)
    )

    override val chatRoomUseCases = ChatRoomUseCases(
        createChatRoomUseCase = CreateChatRoomUseCase(chatRoomRepository),
        readChatRoomUseCase = ReadChatRoomUseCase(chatRoomRepository),
        updateChatRoomUseCase = UpdateChatRoomUseCase(chatRoomRepository),
        deleteChatRoomUseCase = DeleteChatRoomUseCase(chatRoomRepository)
    )

    override val recruitPostingUseCases = RecruitPostingUseCases(
        createRecruitPosting = CreateRecruitPostingUseCase(recruitPostingRepository),
        readRecruitPosting = ReadRecruitPostingUseCase(recruitPostingRepository),
        readRecruitPostingList = ReadRecruitPostingListUseCase(recruitPostingRepository),
        updateRecruitPosting = UpdateRecruitPostingUseCase(recruitPostingRepository),
        deleteRecruitPosting = DeleteRecruitPostingUseCase(recruitPostingRepository)
    )

    override val userSessionUseCases = UserSessionUseCases(
        getUserSession = GetUserSessionUseCase(userSessionRepository),
        checkUserRegisteredUseCase = CheckUserRegisteredUseCase(userRepository),
        updateUserSession = UpdateUserSessionUseCase(userSessionRepository),
        clearUserSession = ClearUserSessionUseCase(userSessionRepository)
    )

    override val messageUseCases = MessageUseCases(
        sendMessageUseCase = SendMessageUseCase(chatRoomRepository),
        observeMessageUseCase = ObserveMessageUseCase(chatRoomRepository),
        markMessageAsReadUseCase = MarkMessageAsReadUseCase(chatRoomRepository),
        getUnreadMessageCountUserCase = GetUnreadMessageCountUseCase(chatRoomRepository),
        getChatParticipantsUseCase = GetChatParticipantsUseCase(chatRoomRepository),
        getUserInfoUseCase = GetUserInfoUseCase(userRepository),
        deleteMessageUseCase = DeleteMessageUseCase(chatRoomRepository)
    )


}