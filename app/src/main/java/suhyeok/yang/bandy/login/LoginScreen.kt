package suhyeok.yang.bandy.login

import android.R.attr.shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import suhyeok.yang.bandy.R
import suhyeok.yang.bandy.component.LoginButton
import suhyeok.yang.bandy.component.LoginButtonText
import suhyeok.yang.bandy.ui.theme.BandyTheme
import suhyeok.yang.bandy.ui.theme.Black
import suhyeok.yang.bandy.ui.theme.KakaoColor
import suhyeok.yang.bandy.ui.theme.KakaoFontColor
import suhyeok.yang.bandy.ui.theme.NaverColor
import suhyeok.yang.bandy.ui.theme.Primary
import suhyeok.yang.bandy.ui.theme.Secondary
import suhyeok.yang.bandy.ui.theme.White

@Composable
fun LoginScreen() {

    Column(
        modifier = Modifier.fillMaxSize().background(Secondary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.3f))
        BandyLogoImageSection()
        Spacer(modifier = Modifier.weight(0.7f))
        LoginButtonsSection()
    }
}

@Composable
fun BandyLogoImageSection() {
    Image(
        painter = painterResource(id = R.drawable.bandy_logo_primary_color),
        contentDescription = null,
        modifier = Modifier.size(
            dimensionResource(R.dimen.login_screen_bandy_logo_width),
            dimensionResource(R.dimen.login_screen_bandy_logo_height)
        )
    )
}

@Composable
fun LoginButtonsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.login_button_section_bottom_padding))
            .padding(horizontal = dimensionResource(R.dimen.login_btn_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.login_btn_space)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginText()
        KakaoLoginButton()
        NaverLoginButton()
    }
}

@Composable
fun LoginText() {
    Text(
        text = stringResource(R.string.login_text),
        color = Primary,
        fontSize = dimensionResource(R.dimen.login_text).value.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.login_text_bottom_padding))
    )
}

@Composable
fun KakaoLoginButton() {
    LoginButton(
        backgroundColor = KakaoColor,
        onClick = {
            // TODO 카카오 로그인 처리
        },
        content = {
            LoginButtonText(
                text = stringResource(R.string.kakao_login_btn_text),
                fontColor = KakaoFontColor
            )
        }
    )
}

@Composable
fun NaverLoginButton() {
    LoginButton(
        backgroundColor = NaverColor,
        onClick = {
            // TODO 네이버 로그인 처리
        },
        content = {
            LoginButtonText(
                text = stringResource(R.string.naver_login_btn_text),
                fontColor = White
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    BandyTheme {
        LoginScreen()
    }
}