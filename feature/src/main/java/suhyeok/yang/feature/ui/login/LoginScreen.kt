package suhyeok.yang.feature.ui.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.collectLatest
import suhyeok.yang.data.login.LoginManager
import suhyeok.yang.feature.R
import suhyeok.yang.shared.common.component.LoginButton
import suhyeok.yang.shared.common.component.LoginButtonText
import suhyeok.yang.shared.ui.theme.BandyTheme
import suhyeok.yang.shared.ui.theme.KakaoColor
import suhyeok.yang.shared.ui.theme.KakaoFontColor
import suhyeok.yang.shared.ui.theme.NaverColor
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.Secondary
import suhyeok.yang.shared.ui.theme.White

@Composable
fun LoginScreen(
    context: Context
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Secondary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.3f))
        BandyLogoImageSection()
        Spacer(modifier = Modifier.weight(0.7f))
        LoginButtonsSection(context)
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
fun LoginButtonsSection(
    context: Context
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.login_button_section_bottom_padding))
            .padding(horizontal = dimensionResource(R.dimen.login_btn_section_horizontal_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.login_btn_space)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginText()
        // KakaoLoginButton(onLoginSuccess)
        NaverLoginButton(context)
    }
}

@Composable
fun LoginText() {
    Text(
        text = stringResource(R.string.start_login_text),
        color = Primary,
        fontSize = dimensionResource(R.dimen.login_text).value.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.login_text_bottom_padding))
    )
}

//@Composable
//fun KakaoLoginButton(onLoginSuccess: () -> Unit) {
//    LoginButton(
//        backgroundColor = KakaoColor,
//        // TODO 카카오 로그인 수정 필요
//        onClick = onLoginSuccess,
//        content = {
//            LoginButtonText(
//                text = stringResource(R.string.kakao_login_btn_text),
//                fontColor = KakaoFontColor
//            )
//        }
//    )
//}

@Composable
fun NaverLoginButton(context: Context) {
    LoginButton(
        backgroundColor = NaverColor,
        onClick = {
            LoginManager.naverLogin(context)
        },
        content = {
            LoginButtonText(
                text = stringResource(R.string.naver_login_btn_text),
                fontColor = White
            )
        }
    )
}
