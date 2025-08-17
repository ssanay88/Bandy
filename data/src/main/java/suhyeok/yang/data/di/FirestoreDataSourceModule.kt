package suhyeok.yang.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FirestoreDataSourceModule {

    @Binds
    @Singleton
    abstract fun provideUserDataSource(firestoreUserDataSourceImpl: FirestoreUserDataSourceImpl): UserDataSource

    @Binds
    @Singleton
    abstract fun provideBandDataSource(firestoreBandDataSourceImpl: FirestoreBandDataSourceImpl): BandDataSource

    @Binds
    @Singleton
    abstract fun provideHomeTopBannerDataSource(firestoreHomeTopBannerDataSourceImpl: FirestoreHomeTopBannerDataSourceImpl): HomeTopBannerDataSource

    @Binds
    @Singleton
    abstract fun providePostingDataSource(firestorePostingDataSourceImpl: FirestorePostingDataSourceImpl): PostingDataSource

    @Binds
    @Singleton
    abstract fun provideRecruitPostingDataSource(firestoreRecruitPostingDataSourceImpl: FirestoreRecruitPostingDataSourceImpl): RecruitPostingDataSource

    @Binds
    @Singleton
    abstract fun provideChatRoomDataSource(firestoreChatRoomDataSourceImpl: FirestoreChatRoomDataSourceImpl): ChatRoomDataSource

    @Binds
    @Singleton
    abstract fun provideUserSessionDataSource(userSessionDataSourceImpl: UserSessionDataSourceImpl): UserSessionDataSource
}