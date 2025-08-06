package suhyeok.yang.bandy.main_nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import suhyeok.yang.feature.ui.chat.ChatScreen
import suhyeok.yang.bandy.MainScreenRoute
import suhyeok.yang.bandy.NestedScreenRoute
import suhyeok.yang.feature.factory.BandInfoViewModelFactory
import suhyeok.yang.feature.factory.ChatViewModelFactory
import suhyeok.yang.feature.factory.CreateBandViewModelFactory
import suhyeok.yang.feature.factory.FirestoreSettingViewModelFactory
import suhyeok.yang.feature.factory.HomeViewModelFactory
import suhyeok.yang.feature.factory.MyBandViewModelFactory
import suhyeok.yang.feature.factory.PostingDetailViewModelFactory
import suhyeok.yang.feature.factory.ProfileViewModelFactory
import suhyeok.yang.feature.factory.RecruitViewModelFactory
import suhyeok.yang.feature.ui.band.CreateBandScreen
import suhyeok.yang.feature.screen.CreateRecruitingMemberScreen
import suhyeok.yang.feature.ui.home.HomeScreen
import suhyeok.yang.feature.screen.ManageBandScreen
import suhyeok.yang.feature.ui.myband.MyBandScreen
import suhyeok.yang.feature.screen.NotificationScreen
import suhyeok.yang.feature.ui.posting.PostingDetailScreen
import suhyeok.yang.feature.ui.posting.PostingHistoryScreen
import suhyeok.yang.feature.ui.profile.ProfileScreen
import suhyeok.yang.feature.screen.ProfileUpdateScreen
import suhyeok.yang.feature.ui.recruit.RecruitScreen
import suhyeok.yang.feature.ui.recruit.RecruitingMemberScreen
import suhyeok.yang.feature.ui.band.BandInfoScreen
import suhyeok.yang.feature.ui.band.BandInfoViewModel
import suhyeok.yang.feature.ui.band.CreateBandViewModel
import suhyeok.yang.feature.ui.chat.ChatViewModel
import suhyeok.yang.feature.viewmodel.FirestoreSettingViewModel
import suhyeok.yang.feature.ui.home.HomeViewModel
import suhyeok.yang.feature.ui.myband.MyBandViewModel
import suhyeok.yang.feature.ui.posting.PostingDetailViewModel
import suhyeok.yang.feature.ui.profile.ProfileViewModel
import suhyeok.yang.feature.ui.recruit.RecruitViewModel
import suhyeok.yang.shared.di.ApplicationContainerProvider

@Composable
fun BandyNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: MainScreenRoute = MainScreenRoute.HomeScreen
) {
    // DI
    val applicationContext = LocalContext.current.applicationContext
    val userUseCases =
        (applicationContext as ApplicationContainerProvider).provideApplicationContainer().userUseCases
    val bandUseCases =
        (applicationContext as ApplicationContainerProvider).provideApplicationContainer().bandUseCases
    val postingUseCases =
        (applicationContext as ApplicationContainerProvider).provideApplicationContainer().postingUseCases
    val recruitPostingUseCases =
        (applicationContext as ApplicationContainerProvider).provideApplicationContainer().recruitPostingUseCases
    val homeTopBannerUseCases =
        (applicationContext as ApplicationContainerProvider).provideApplicationContainer().homeTopBannerUseCases
    val chatRoomUseCases =
        (applicationContext as ApplicationContainerProvider).provideApplicationContainer().chatRoomUseCases
    val userSessionUseCases =
        (applicationContext as ApplicationContainerProvider).provideApplicationContainer().userSessionUseCases

    val firestoreSettingFactory = FirestoreSettingViewModelFactory(
        userUseCases,
        bandUseCases,
        postingUseCases,
        recruitPostingUseCases,
        chatRoomUseCases
    )
    val firestoreSettingViewModel: FirestoreSettingViewModel =
        viewModel(factory = firestoreSettingFactory)

    val homeFactory = HomeViewModelFactory(homeTopBannerUseCases, bandUseCases, postingUseCases)
    val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)

    val recruitFactory = RecruitViewModelFactory(bandUseCases, recruitPostingUseCases)
    val recruitViewModel: RecruitViewModel = viewModel(factory = recruitFactory)

    val myBandFactory = MyBandViewModelFactory(bandUseCases, userSessionUseCases)
    val myBandViewModel: MyBandViewModel = viewModel(factory = myBandFactory)

    val chatFactory = ChatViewModelFactory(userSessionUseCases, chatRoomUseCases)
    val chatViewModel: ChatViewModel = viewModel(factory = chatFactory)

    val bandInfoFactory = BandInfoViewModelFactory(bandUseCases)
    val bandInfoViewModel: BandInfoViewModel = viewModel(factory = bandInfoFactory)

    val profileFactory = ProfileViewModelFactory(userSessionUseCases)
    val profileViewModel: ProfileViewModel = viewModel(factory = profileFactory)

    val postingDetailFactory = PostingDetailViewModelFactory(postingUseCases, userSessionUseCases, userUseCases)
    val postingDetailViewModel: PostingDetailViewModel = viewModel(factory = postingDetailFactory)

    val createBandFactory = CreateBandViewModelFactory(userUseCases, bandUseCases, userSessionUseCases)
    val createBandViewModel: CreateBandViewModel = viewModel(factory = createBandFactory)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

//        Firestore에 등록이 필요한 경우
        firestoreSettingViewModel.apply {
            //uploadUserData(MockData.mockUsers)
            //updateUserData(MockData.mockUsers)
            //uploadBandData(MockData.mockBandList)
            // updateBandData(MockData.mockBandList)
            //uploadPosting(MockData.postingList)
            // updatePosting(MockData.postingList)
            //uploadRecruitPosting(MockData.mockRecruitPostingList)
            // uploadChatRoom(MockData.chatRoomList)
            // updateChatRoom(MockData.chatRoomList)
        }


        // MainScreen
        composable<MainScreenRoute.HomeScreen> { navBackStackEntry ->
            HomeScreen(
                viewModel = homeViewModel,
                onPopularBandClick = { bandId: String ->
                    navController.navigate(
                        NestedScreenRoute.BandInfoScreen(
                            bandId
                        )
                    )
                },
                onPostingClick = { postingId: String ->
                    navController.navigate(
                        NestedScreenRoute.PostingDetailScreen(
                            postingId
                        )
                    )
                }
            )
        }
        composable<MainScreenRoute.RecruitScreen> {
            RecruitScreen(
                viewModel = recruitViewModel,
                onBandInfoClick = { bandId ->
                    navController.navigate(
                        NestedScreenRoute.BandInfoScreen(bandId)
                    )
                },
                onRecruitingMemberClick = { recruitPostingId ->
                    navController.navigate(
                        NestedScreenRoute.RecruitingMemberScreen(recruitPostingId)
                    )
                }
            )
        }
        composable<MainScreenRoute.MyBandScreen> {
            MyBandScreen(
                viewModel = myBandViewModel,
                onSuggestFindBandClick = {
                    navController.navigate(MainScreenRoute.RecruitScreen)
                }
            )
        }
        composable<MainScreenRoute.ChatScreen> {
            ChatScreen(
                viewModel = chatViewModel,
                onChatRoomClick = {
                    navController.navigate(NestedScreenRoute.ChatRoomScreen)
                }
            )
        }
        composable<MainScreenRoute.ProfileScreen> {
            ProfileScreen(
                viewModel = profileViewModel,
                onUpdateProfileClick = { navController.navigate(NestedScreenRoute.ProfileUpdateScreen) },
                onPostingsHistoryClick = { navController.navigate(NestedScreenRoute.PostingHistoryScreen) },
                onManageBandClick = { navController.navigate(NestedScreenRoute.ManageBandScreen) },
                onNotificationClick = { navController.navigate(NestedScreenRoute.NotificationScreen) },
            )
        }

        // NestedScreen
        composable<NestedScreenRoute.BandInfoScreen> { navBackStackEntry ->
            val bandId = navBackStackEntry.toRoute<NestedScreenRoute.BandInfoScreen>().bandId
            BandInfoScreen(bandInfoViewModel, bandId)
        }
        composable<NestedScreenRoute.PostingDetailScreen> { navBackStackEntry ->
            val postingId =
                navBackStackEntry.toRoute<NestedScreenRoute.PostingDetailScreen>().postingId
            PostingDetailScreen(postingId, postingDetailViewModel)
        }
        composable<NestedScreenRoute.RecruitingMemberScreen> {navBackStackEntry ->
            val recruitPostingId = navBackStackEntry.toRoute<NestedScreenRoute.RecruitingMemberScreen>().recruitingPostingId
            RecruitingMemberScreen(recruitPostingId)
        }
        composable<NestedScreenRoute.ProfileUpdateScreen> {
            ProfileUpdateScreen(
                navController = navController,
                {
                    // TODO 프로필 업데이트 버튼 클릭?
                }
            )
        }
        composable<NestedScreenRoute.PostingHistoryScreen> {
            PostingHistoryScreen()
        }
        composable<NestedScreenRoute.ManageBandScreen> {
            ManageBandScreen(navController)
        }
        composable<NestedScreenRoute.CreateBandScreen> {
            CreateBandScreen(createBandViewModel, navController) {
                navController.navigate(MainScreenRoute.MyBandScreen)
            }
        }
        composable<NestedScreenRoute.CreateRecruitingMemberScreen> {
            CreateRecruitingMemberScreen(navController)
        }
        composable<NestedScreenRoute.NotificationScreen> {
            NotificationScreen()
        }
    }
}

