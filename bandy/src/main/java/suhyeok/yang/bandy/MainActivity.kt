package suhyeok.yang.bandy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import suhyeok.yang.shared.ui.theme.BandyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BandyTheme {
                EntryScreenWithScaffold()
            }
        }
    }
}
