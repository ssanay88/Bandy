package suhyeok.yang.bandy.recruit

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import suhyeok.yang.bandy.ui.theme.BandyTheme

@Composable
fun RecruitScreen(navController: NavController) {

}

@Preview(showBackground = true)
@Composable
fun RecruitScreenPreview(navController: NavHostController = rememberNavController()) {
    BandyTheme {
        RecruitScreen(navController)
    }
}