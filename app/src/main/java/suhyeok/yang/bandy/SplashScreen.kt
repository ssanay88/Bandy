package suhyeok.yang.bandy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import suhyeok.yang.bandy.ui.theme.Primary
import suhyeok.yang.bandy.utils.toSp

const val SPLASH_TIMEOUT = 2000L

@Composable
fun SplashScreen(onTimeout: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SplashScreenAppName()
    }

    LaunchedEffect(true) {
        delay(SPLASH_TIMEOUT)
        onTimeout()
    }

}

@Composable
fun SplashScreenAppName() {
    Text(
        text = stringResource(R.string.app_name),
        fontSize = dimensionResource(R.dimen.splash_app_name_font_size).toSp(),
        color = Primary,
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen {}
}

