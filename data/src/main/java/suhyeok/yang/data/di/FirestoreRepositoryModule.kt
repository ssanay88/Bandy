package suhyeok.yang.data.di

import com.yang.business.repository.BandRepository
import com.yang.business.repository.ChatRoomRepository
import com.yang.business.repository.HomeTopBannerRepository
import com.yang.business.repository.PostingHistoryRepository
import com.yang.business.repository.PostingRepository
import com.yang.business.repository.RecruitPostingRepository
import com.yang.business.repository.UserRepository
import com.yang.business.repository.UserSessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import suhyeok.yang.data.repository.DatastoreUserSessionRepositoryImpl
import suhyeok.yang.data.repository.FirestoreBandRepositoryImpl
import suhyeok.yang.data.repository.FirestoreChatRoomRepositoryImpl
import suhyeok.yang.data.repository.FirestoreHomeTopBannerRepositoryImpl
import suhyeok.yang.data.repository.FirestorePostingHistoryRepositoryImpl
import suhyeok.yang.data.repository.FirestorePostingRepositoryImpl
import suhyeok.yang.data.repository.FirestoreRecruitPostingRepositoryImpl
import suhyeok.yang.data.repository.FirestoreUserRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class FirestoreRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(firestoreUserRepositoryImpl: FirestoreUserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindBandRepository(firestoreBandRepositoryImpl: FirestoreBandRepositoryImpl): BandRepository

    @Binds
    @Singleton
    abstract fun bindHomeTopBannerRepository(firestoreHomeTopBannerRepositoryImpl: FirestoreHomeTopBannerRepositoryImpl): HomeTopBannerRepository

    @Binds
    @Singleton
    abstract fun bindPostingRepository(firestorePostingRepositoryImpl: FirestorePostingRepositoryImpl): PostingRepository

    @Binds
    @Singleton
    abstract fun bindRecruitPostingRepository(firestoreRecruitPostingRepositoryImpl: FirestoreRecruitPostingRepositoryImpl): RecruitPostingRepository

    @Binds
    @Singleton
    abstract fun bindChatRoomRepository(firestoreChatRoomRepositoryImpl: FirestoreChatRoomRepositoryImpl): ChatRoomRepository

    @Binds
    @Singleton
    abstract fun bindUserSessionRepository(datastoreUserSessionRepositoryImpl: DatastoreUserSessionRepositoryImpl): UserSessionRepository

    @Binds
    @Singleton
    abstract fun bindPostingHistoryRepository(firestorePostingRepositoryImpl: FirestorePostingHistoryRepositoryImpl): PostingHistoryRepository

}