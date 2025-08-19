package suhyeok.yang.bandy.main_nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.yang.business.enums.RecruitScreenTab
import suhyeok.yang.feature.ui.chat.ChatScreen
import suhyeok.yang.bandy.MainScreenRoute
import suhyeok.yang.bandy.NestedScreenRoute
import suhyeok.yang.feature.nav.NavKeys
import suhyeok.yang.feature.ui.band.CreateBandScreen
import suhyeok.yang.feature.ui.recruitmember.CreateRecruitingMemberScreen
import suhyeok.yang.feature.ui.home.HomeScreen
import suhyeok.yang.feature.screen.ManageBandScreen
import suhyeok.yang.feature.ui.myband.MyBandScreen
import suhyeok.yang.feature.screen.NotificationScreen
import suhyeok.yang.feature.ui.posting.PostingDetailScreen
import suhyeok.yang.feature.ui.profile.ProfileScreen
import suhyeok.yang.feature.ui.profile.ProfileUpdateScreen
import suhyeok.yang.feature.ui.recruit.RecruitScreen
import suhyeok.yang.feature.ui.recruit.RecruitingMemberScreen
import suhyeok.yang.feature.ui.band.BandInfoScreen
import suhyeok.yang.feature.ui.chat.ChatRoomScreen
import suhyeok.yang.feature.viewmodel.FirestoreSettingViewModel
import suhyeok.yang.feature.ui.posting.CreatePostingScreen
import suhyeok.yang.feature.ui.profile.PostingHistoryScreen

@Composable
fun BandyNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: MainScreenRoute = MainScreenRoute.HomeScreen
) {
    val firestoreSettingViewModel: FirestoreSettingViewModel = hiltViewModel()

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
                navController = navController,
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
                onSuggestFindBandClick = {
                    navController.navigate(MainScreenRoute.RecruitScreen())
                }
            )
        }
        composable<MainScreenRoute.ChatScreen> {
            ChatScreen(
                onChatRoomClick = { chatRoomId, currentUserId ->
                    navController.navigate(NestedScreenRoute.ChatRoomScreen(chatRoomId, currentUserId))
                }
            )
        }
        composable<MainScreenRoute.ProfileScreen> {
            ProfileScreen(
                onUpdateProfileClick = { navController.navigate(NestedScreenRoute.ProfileUpdateScreen) },
                onPostingsHistoryClick = { navController.navigate(NestedScreenRoute.PostingHistoryScreen) },
                onManageBandClick = { navController.navigate(NestedScreenRoute.ManageBandScreen) },
                onNotificationClick = { navController.navigate(NestedScreenRoute.NotificationScreen) },
            )
        }

        // NestedScreen
        composable<NestedScreenRoute.BandInfoScreen> { navBackStackEntry ->
            val bandId = navBackStackEntry.toRoute<NestedScreenRoute.BandInfoScreen>().bandId
            BandInfoScreen(bandId)
        }
        composable<NestedScreenRoute.PostingDetailScreen> { navBackStackEntry ->
            val postingId = navBackStackEntry.toRoute<NestedScreenRoute.PostingDetailScreen>().postingId
            PostingDetailScreen(postingId)
        }
        composable<NestedScreenRoute.RecruitingMemberScreen> { navBackStackEntry ->
            val recruitPostingId = navBackStackEntry.toRoute<NestedScreenRoute.RecruitingMemberScreen>().recruitingPostingId
            RecruitingMemberScreen(recruitPostingId, navController)
        }
        composable<NestedScreenRoute.ChatRoomScreen> { navBackStackEntry ->
            val chatRoomId = navBackStackEntry.toRoute<NestedScreenRoute.ChatRoomScreen>().chatRoomId
            val currentUserId = navBackStackEntry.toRoute<NestedScreenRoute.ChatRoomScreen>().currentUserId
            ChatRoomScreen(chatRoomId, currentUserId)
        }
        composable<NestedScreenRoute.ProfileUpdateScreen> {
            ProfileUpdateScreen(
                onCancelClick = { navController.popBackStack() },
                onUpdateClick = { navController.navigate(MainScreenRoute.ProfileScreen) }
            )
        }
        composable<NestedScreenRoute.PostingHistoryScreen> {
            PostingHistoryScreen(
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
                onCancelClick = { navController.popBackStack() },
                onCreateBandClick = {
                    navController.navigate(MainScreenRoute.MyBandScreen)
                }
            )
        }
        composable<NestedScreenRoute.CreateRecruitingMemberScreen> {
            CreateRecruitingMemberScreen(
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
                onCreatePostingComplete = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(NavKeys.CREATE_POSTING_COMPLETE_KEY, true)

                    navController.navigate(MainScreenRoute.HomeScreen) {
                        popUpTo(MainScreenRoute.HomeScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

