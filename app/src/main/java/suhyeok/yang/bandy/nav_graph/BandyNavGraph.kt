package suhyeok.yang.bandy.nav_graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import suhyeok.yang.bandy.SplashScreen
import suhyeok.yang.bandy.home.HomeScreen
import suhyeok.yang.bandy.login.LoginScreen
import suhyeok.yang.bandy.myband.MyBandScreen
import suhyeok.yang.bandy.profile.ProfileScreen
import suhyeok.yang.bandy.recruit.RecruitScreen

@Composable
fun BandyNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = BandyDestinations.SPLASH_ROUTE
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = BandyDestinations.SPLASH_ROUTE) {
            SplashScreen { navController.navigate(BandyDestinations.LOGIN_ROUTE) }
        }
        composable(route = BandyDestinations.LOGIN_ROUTE) {
            LoginScreen()
        }
        composable(route = BandyDestinations.HOME_ROUTE) {
            HomeScreen(navController = navController)
        }
        composable(route = BandyDestinations.RECRUIT_ROUTE) {
            RecruitScreen(navController = navController)
        }
        composable(route = BandyDestinations.MY_BAND_ROUTE) {
            MyBandScreen(navController = navController)
        }
        composable(route = BandyDestinations.PROFILE_ROUTE) {
            ProfileScreen(navController = navController)
        }
    }
}

