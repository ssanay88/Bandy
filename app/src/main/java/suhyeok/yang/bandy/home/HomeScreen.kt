package suhyeok.yang.bandy.home

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import suhyeok.yang.bandy.ui.theme.BandyTheme

@Composable
fun HomeScreen(navController: NavController) {

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(navController: NavController = rememberNavController()) {
    BandyTheme {
        HomeScreen(navController)
    }
}