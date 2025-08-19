package suhyeok.yang.bandy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import suhyeok.yang.feature.screen.SplashScreen
import suhyeok.yang.feature.viewmodel.SplashActivityViewModel
import suhyeok.yang.feature.viewmodel.SplashUiState
import suhyeok.yang.shared.ui.theme.BandyTheme

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel by viewModels<SplashActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
