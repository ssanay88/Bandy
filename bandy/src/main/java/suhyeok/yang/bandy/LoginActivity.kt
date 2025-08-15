package suhyeok.yang.bandy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import suhyeok.yang.bandy.login_nav.LoginNavGraph
import suhyeok.yang.shared.ui.theme.BandyTheme

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BandyTheme {
                LoginNavGraph(
                    modifier = Modifier.fillMaxSize(),
                    navController = rememberNavController()
                )
            }
        }
    }
}
