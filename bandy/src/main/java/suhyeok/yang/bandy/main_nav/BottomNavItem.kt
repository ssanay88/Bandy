package suhyeok.yang.bandy.main_nav

import suhyeok.yang.shared.R
import suhyeok.yang.bandy.MainScreenRoute

data class BottomNavItem(
    val tabTitleResId: Int = 0,
    val tabIcon: Int = 0,
    val destination: MainScreenRoute = MainScreenRoute.HomeScreen,
    val route: String = ""
) {
    fun getBottomNavItems(): List<BottomNavItem> {
        return listOf(
            BottomNavItem(
                tabTitleResId = R.string.home_screen_title,
                tabIcon = R.drawable.ic_outline_home_tab,
                destination = MainScreenRoute.HomeScreen,
                route = "HomeScreen"
            ),
            BottomNavItem(
                tabTitleResId = R.string.recruit_screen_title,
                tabIcon = R.drawable.ic_outline_recruit_tab,
                destination = MainScreenRoute.RecruitScreen,
                route = "RecruitScreen"
            ),
            BottomNavItem(
                tabTitleResId = R.string.my_band_screen_title,
                tabIcon = R.drawable.ic_outline_my_band_tab,
                destination = MainScreenRoute.MyBandScreen,
                route = "MyBandScreen"
            ),
            BottomNavItem(
                tabTitleResId = R.string.chat_screen_title,
                tabIcon = R.drawable.ic_outline_chat,
                destination = MainScreenRoute.ChatScreen,
                route = "ChatScreen"
            ),
            BottomNavItem(
                tabTitleResId = R.string.profile_screen_title,
                tabIcon = R.drawable.ic_outline_profile_tab,
                destination = MainScreenRoute.ProfileScreen,
                route = "ProfileScreen"
            )
        )
    }
}
