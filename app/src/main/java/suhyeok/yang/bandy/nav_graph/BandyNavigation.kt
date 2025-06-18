package suhyeok.yang.bandy.nav_graph

import suhyeok.yang.bandy.nav_graph.BandyScreensRoute.HOME_SCREEN
import suhyeok.yang.bandy.nav_graph.BandyScreensRoute.LOGIN_SCREEN
import suhyeok.yang.bandy.nav_graph.BandyScreensRoute.MY_BAND_SCREEN
import suhyeok.yang.bandy.nav_graph.BandyScreensRoute.RECRUIT_SCREEN
import suhyeok.yang.bandy.nav_graph.BandyScreensRoute.SPLASH_SCREEN
import suhyeok.yang.bandy.nav_graph.BandyScreensRoute.PROFILE_SCREEN


private object BandyScreensRoute {
    const val SPLASH_SCREEN = "splash"
    const val LOGIN_SCREEN = "login"
    const val HOME_SCREEN = "home"
    const val RECRUIT_SCREEN = "recruit"
    const val MY_BAND_SCREEN = "myband"
    const val PROFILE_SCREEN = "profile"
}

object BottomNavTabName {
    const val HOME_TAB = "홈"
    const val RECRUIT_TAB = "모집"
    const val MY_BAND_TAB = "마이 밴드"
    const val PROFILE_TAB = "프로필"
}

/**
 * Navigation에서 루트를 지정하기 위한 목적지
 */
object BandyDestinations {
    const val SPLASH_ROUTE = SPLASH_SCREEN
    const val LOGIN_ROUTE = LOGIN_SCREEN
    const val HOME_ROUTE = HOME_SCREEN
    const val RECRUIT_ROUTE = RECRUIT_SCREEN
    const val MY_BAND_ROUTE = MY_BAND_SCREEN
    const val PROFILE_ROUTE = PROFILE_SCREEN

}
