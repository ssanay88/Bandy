package suhyeok.yang.bandy.main_nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.yang.business.enums.RecruitScreenTab
import suhyeok.yang.feature.ui.chat.ChatScreen
import suhyeok.yang.bandy.MainScreenRoute
import suhyeok.yang.bandy.NestedScreenRoute
import suhyeok.yang.feature.factory.BandInfoViewModelFactory
import suhyeok.yang.feature.factory.ChatViewModelFactory
import suhyeok.yang.feature.factory.CreateBandViewModelFactory
import suhyeok.yang.feature.factory.CreatePostingViewModelFactory
import suhyeok.yang.feature.factory.CreateRecruitingMemberViewModelFactory
import suhyeok.yang.feature.factory.FirestoreSettingViewModelFactory
import suhyeok.yang.feature.factory.HomeViewModelFactory
import suhyeok.yang.feature.factory.MyBandViewModelFactory
import suhyeok.yang.feature.factory.PostingDetailViewModelFactory
import suhyeok.yang.feature.factory.PostingHistoryViewModelFactory
import suhyeok.yang.feature.factory.ProfileUpdateViewModelFactory
import suhyeok.yang.feature.factory.ProfileViewModelFactory
import suhyeok.yang.feature.factory.RecruitViewModelFactory
import suhyeok.yang.feature.factory.RecruitingMemberViewModelFactory
import suhyeok.yang.feature.ui.band.CreateBandScreen
import suhyeok.yang.feature.ui.recruitmember.CreateRecruitingMemberScreen
import suhyeok.yang.feature.ui.home.HomeScreen
import suhyeok.yang.feature.screen.ManageBandScreen
import suhyeok.yang.feature.ui.myband.MyBandScreen
import suhyeok.yang.feature.screen.NotificationScreen
import suhyeok.yang.feature.ui.posting.PostingDetailScreen
import suhyeok.yang.feature.ui.posting.PostingHistoryScreen
import suhyeok.yang.feature.ui.profile.ProfileScreen
import suhyeok.yang.feature.ui.profile.ProfileUpdateScreen
import suhyeok.yang.feature.ui.recruit.RecruitScreen
import suhyeok.yang.feature.ui.recruit.RecruitingMemberScreen
import suhyeok.yang.feature.ui.band.BandInfoScreen
import suhyeok.yang.feature.ui.band.BandInfoViewModel
import suhyeok.yang.feature.ui.band.CreateBandViewModel
import suhyeok.yang.feature.ui.chat.ChatViewModel
import suhyeok.yang.feature.viewmodel.FirestoreSettingViewModel
import suhyeok.yang.feature.ui.home.HomeViewModel
import suhyeok.yang.feature.ui.myband.MyBandViewModel
import suhyeok.yang.feature.ui.posting.CreatePostingScreen
import suhyeok.yang.feature.ui.posting.CreatePostingViewModel
import suhyeok.yang.feature.ui.posting.PostingDetailViewModel
import suhyeok.yang.feature.ui.profile.PostingHistoryScreen
import suhyeok.yang.feature.ui.profile.PostingHistoryViewModel
import suhyeok.yang.feature.ui.profile.ProfileUpdateViewModel
import suhyeok.yang.feature.ui.profile.ProfileViewModel
import suhyeok.yang.feature.ui.recruit.RecruitViewModel
import suhyeok.yang.feature.ui.recruit.RecruitingMemberViewModel
import suhyeok.yang.feature.ui.recruitmember.CreateRecruitingMemberViewModel
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
    val postingHistoryUseCases =
        (applicationContext as ApplicationContainerProvider).provideApplicationContainer().postingHistoryUseCases
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

    val postingDetailFactory =
        PostingDetailViewModelFactory(postingUseCases, userSessionUseCases, userUseCases)
    val postingDetailViewModel: PostingDetailViewModel = viewModel(factory = postingDetailFactory)

    val createPostingFactory = CreatePostingViewModelFactory(postingUseCases, userSessionUseCases)
    val createPostingViewModel: CreatePostingViewModel = viewModel(factory = createPostingFactory)

    val postingHistoryFactory = PostingHistoryViewModelFactory(userSessionUseCases, postingHistoryUseCases)
    val postingHistoryViewModel: PostingHistoryViewModel = viewModel(factory = postingHistoryFactory)

    val createBandFactory =
        CreateBandViewModelFactory(userUseCases, bandUseCases, userSessionUseCases)
    val createBandViewModel: CreateBandViewModel = viewModel(factory = createBandFactory)

    val createRecruitingMemberFactory = CreateRecruitingMemberViewModelFactory(
        userSessionUseCases,
        bandUseCases,
        recruitPostingUseCases
    )
    val createRecruitingMemberViewModel: CreateRecruitingMemberViewModel =
        viewModel(factory = createRecruitingMemberFactory)

    val recruitingMemberFactory =
        RecruitingMemberViewModelFactory(userSessionUseCases, bandUseCases, recruitPostingUseCases)
    val recruitingMemberViewModel: RecruitingMemberViewModel =
        viewModel(factory = recruitingMemberFactory)

    val profileUpdateFactory = ProfileUpdateViewModelFactory(userSessionUseCases, userUseCases)
    val profileUpdateViewModel: ProfileUpdateViewModel = viewModel(factory = profileUpdateFactory)

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
        composable<MainScreenRoute.RecruitScreen> { navBackStackEntry ->
            val currentTab = navBackStackEntry.toRoute<MainScreenRoute.RecruitScreen>().currentTab
            RecruitScreen(
                viewModel = recruitViewModel,
                currentTab = currentTab,
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
                    navController.navigate(MainScreenRoute.RecruitScreen())
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
            val postingId = navBackStackEntry.toRoute<NestedScreenRoute.PostingDetailScreen>().postingId
            PostingDetailScreen(postingId, postingDetailViewModel)
        }
        composable<NestedScreenRoute.RecruitingMemberScreen> { navBackStackEntry ->
            val recruitPostingId = navBackStackEntry.toRoute<NestedScreenRoute.RecruitingMemberScreen>().recruitingPostingId
            RecruitingMemberScreen(recruitPostingId, navController, recruitingMemberViewModel)
        }
        composable<NestedScreenRoute.ProfileUpdateScreen> {
            ProfileUpdateScreen(
                viewModel = profileUpdateViewModel,
                onCancelClick = { navController.popBackStack() },
                onUpdateClick = { navController.navigate(MainScreenRoute.ProfileScreen) }
            )
        }
        composable<NestedScreenRoute.PostingHistoryScreen> {
            PostingHistoryScreen(
                viewModel = postingHistoryViewModel,
                onPostingClick = { postingId ->
                    navController.navigate(NestedScreenRoute.PostingDetailScreen(postingId))
                }
            )
        }
        composable<NestedScreenRoute.ManageBandScreen> {
            ManageBandScreen(onCancelClick = { navController.popBackStack() })
        }
        composable<NestedScreenRoute.CreateBandScreen> {
            CreateBandScreen(
                viewModel = createBandViewModel,
                onCancelClick = { navController.popBackStack() },
                onCreateBandClick = {
                    navController.navigate(MainScreenRoute.MyBandScreen)
                }
            )
        }
        composable<NestedScreenRoute.CreateRecruitingMemberScreen> {
            CreateRecruitingMemberScreen(
                viewModel = createRecruitingMemberViewModel,
                onCancelClick = { navController.popBackStack() },
                onCreateRecruitingClick = {
                    navController.navigate(MainScreenRoute.RecruitScreen(RecruitScreenTab.MEMBER_RECRUIT_TAB))
                }
            )
        }
        composable<NestedScreenRoute.NotificationScreen> {
            NotificationScreen()
        }
        composable<NestedScreenRoute.CreatePostingScreen> {
            CreatePostingScreen(
                createPostingViewModel
            )
        }
    }
}

