package suhyeok.yang.bandy.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import suhyeok.yang.bandy.R
import suhyeok.yang.bandy.ui.theme.Primary
import suhyeok.yang.bandy.ui.theme.Tertiary
import suhyeok.yang.bandy.ui.theme.White

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    onClick: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    IconButton(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.login_btn_padding))
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.login_btn_height))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.login_btn_corner)))
            .background(backgroundColor),
        onClick = onClick,
        content = content
    )
}

@Composable
fun FilledButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit = {}
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary,
            contentColor = White
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.filled_btn_height)),
        content = content
    )
}


@Composable
fun OutLinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit = {}
) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent,
            contentColor = Primary
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.outlined_btn_height)),
        content = content
    )
}