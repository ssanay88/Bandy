package suhyeok.yang.bandy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import suhyeok.yang.feature.factory.SplashActivityViewModelFactory
import suhyeok.yang.feature.screen.SplashScreen
import suhyeok.yang.feature.viewmodel.SplashActivityViewModel
import suhyeok.yang.feature.viewmodel.SplashUiState
import suhyeok.yang.shared.di.ApplicationContainerProvider
import suhyeok.yang.shared.ui.theme.BandyTheme

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private lateinit var viewModel: SplashActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userSessionUseCases = (applicationContext as ApplicationContainerProvider).provideApplicationContainer().userSessionUseCases
        viewModel = viewModels<SplashActivityViewModel> {
            SplashActivityViewModelFactory(userSessionUseCases)
        }.value

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { uiState ->
                when (uiState) {
                    SplashUiState.NavigateToMainActivity -> {
                        moveToTargetActivity(MainActivity::class.java)
                    }
                    SplashUiState.NavigateToLoginActivity -> {
                        moveToTargetActivity(LoginActivity::class.java)
                    }
                    else -> {}
                }
            }
        }

        enableEdgeToEdge()
        setContent {
            BandyTheme {
                SplashScreen()
            }
        }
    }

    private fun moveToTargetActivity(target: Class<*>) {
        startActivity(Intent(this, target))
        finish()
    }
}
