package suhyeok.yang.feature.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import suhyeok.yang.feature.R

@Composable
fun SplashScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SplashScreenAppName()
    }
}

@Composable
fun SplashScreenAppName() {
    Icon(
        painter = painterResource(R.drawable.bandy_logo_primary_color),
        contentDescription = stringResource(R.string.top_appbar_logo_descript),
        tint = Color.Unspecified,
        modifier = Modifier.fillMaxSize(0.6f)
    )
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}

