package suhyeok.yang.bandy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import suhyeok.yang.bandy.nav_graph.BandyNavGraph
import suhyeok.yang.bandy.ui.theme.BandyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BandyTheme {
                BandyNavGraph()
            }
        }
    }
}
