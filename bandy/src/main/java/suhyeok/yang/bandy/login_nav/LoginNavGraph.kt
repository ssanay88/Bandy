package suhyeok.yang.bandy.login_nav

import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import suhyeok.yang.bandy.MainActivity
import suhyeok.yang.bandy.SubScreenRoute
import suhyeok.yang.feature.factory.LoginViewModelFactory
import suhyeok.yang.feature.factory.ProfileRegViewModelFactory
import suhyeok.yang.feature.ui.login.LoginScreen
import suhyeok.yang.feature.ui.profile.ProfileRegScreen
import suhyeok.yang.feature.ui.login.LoginState
import suhyeok.yang.feature.ui.login.LoginViewModel
import suhyeok.yang.feature.ui.profile.ProfileRegViewModel
import suhyeok.yang.shared.common.component.LoadingScreen
import suhyeok.yang.shared.di.ApplicationContainerProvider

@Composable
fun LoginNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: SubScreenRoute = SubScreenRoute.LoginScreen
) {
    val applicationContext = LocalContext.current.applicationContext
    val userUseCases = (applicationContext as ApplicationContainerProvider).provideApplicationContainer().userUseCases
    val userSessionUseCases = (applicationContext as ApplicationContainerProvider).provideApplicationContainer().userSessionUseCases

    val loginFactory = LoginViewModelFactory(userSessionUseCases)
    val loginViewModel: LoginViewModel = viewModel(factory = loginFactory)

    val profileRegFactory = ProfileRegViewModelFactory(userUseCases, userSessionUseCases)
    val profileRegViewModel: ProfileRegViewModel = viewModel(factory = profileRegFactory)

    val context = LocalContext.current

    val loginState by loginViewModel.loginState.collectAsStateWithLifecycle()
    val newUserId by loginViewModel.newUserId.collectAsStateWithLifecycle()

    when (loginState) {
        is LoginState.FirstLogin -> {
            navController.navigate(SubScreenRoute.ProfileRegScreen(newUserId = newUserId))
        }

        is LoginState.HasProfile -> {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
            (context as? ComponentActivity)?.finish()
        }

        is LoginState.Failure -> {

        }

        is LoginState.Loading -> {
            LoadingScreen()
        }

        else -> {}
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        composable<SubScreenRoute.LoginScreen> {
            LoginScreen(
                loginViewModel
            )
        }
        composable<SubScreenRoute.ProfileRegScreen> {navBackStackEntry ->
            val newUserId = navBackStackEntry.toRoute<SubScreenRoute.ProfileRegScreen>().newUserId
            ProfileRegScreen(profileRegViewModel,newUserId, navController) {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish()
            }
        }
    }
}