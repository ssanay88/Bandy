package suhyeok.yang.bandy

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yang.business.model.TopBarItem
import suhyeok.yang.bandy.main_nav.BandyNavGraph
import suhyeok.yang.feature.appbar.TopAppBar
import suhyeok.yang.feature.fb.HomeFloatingButton
import suhyeok.yang.feature.fb.RecruitFloatingButton
import suhyeok.yang.feature.topBarAsRouteName
import suhyeok.yang.shared.common.component.FilledButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryScreenWithScaffold() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route?.substringBefore("?")?.substringAfterLast(".") ?: ""
    val recruitScreenRoute = MainScreenRoute.RecruitScreen().toString().split("(").first()

    val topBarItem = navController.currentBackStackEntry?.topBarAsRouteName ?: TopBarItem()

    val screensWithBottomNav = listOf(
        MainScreenRoute.HomeScreen.toString(),
        recruitScreenRoute,
        MainScreenRoute.MyBandScreen.toString(),
        MainScreenRoute.ChatScreen.toString(),
        MainScreenRoute.ProfileScreen.toString()
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                topBarItem = topBarItem,
                onLogoIconClick = { navController.navigate(MainScreenRoute.HomeScreen) },
                onChatIconClick = { navController.navigate(MainScreenRoute.ChatScreen) },
                onNotiIconClick = { navController.navigate(NestedScreenRoute.NotificationScreen) },
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            if (currentRoute in screensWithBottomNav) {
                BottomNavBar(navController)
            }
        },
        floatingActionButton = {
            when (currentRoute) {
                recruitScreenRoute -> {
                    RecruitFloatingButton(
                        onCreateBandClick = { navController.navigate(NestedScreenRoute.CreateBandScreen) },
                        onRecruitMemberClick = { navController.navigate(NestedScreenRoute.CreateRecruitingMemberScreen) }
                    )
                }

                MainScreenRoute.HomeScreen.toString() -> {
                    HomeFloatingButton(
                        onCreatePostingClick = { navController.navigate(NestedScreenRoute.CreatePostingScreen) }
                    )
                }

                else -> {}
            }
        }
    ) { innerPadding ->
        BandyNavGraph(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            navController = navController
        )
    }
}