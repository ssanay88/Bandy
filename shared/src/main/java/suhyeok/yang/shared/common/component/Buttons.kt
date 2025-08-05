package suhyeok.yang.shared.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import suhyeok.yang.shared.R
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.ui.theme.BackgroundGray
import suhyeok.yang.shared.ui.theme.Black
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.TextGray
import suhyeok.yang.shared.ui.theme.White

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
            .height(dimensionResource(R.dimen.filled_btn_height))
            .throttleClick { onClick() },
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

@Composable
fun SelectableChip(
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    selected: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    selectedContainerColor: Color = Primary,
    selectedContentColor: Color = White,
    unselectedContainerColor: Color = BackgroundGray,
    unselectedContentColor: Color = Black,
) {
    FilterChip(
        selected = selected,
        onClick = { onClick(selected) },
        label = {
            Text(
                text = text,
                fontFamily = SuitFontFamily,
                style = textStyle
            )
        },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = {
            if (selected) {
                leadingIcon?.invoke()
            } else {
                null
            }
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = selectedContainerColor,
            selectedLabelColor = selectedContentColor,
            selectedLeadingIconColor = selectedContentColor,
            containerColor = unselectedContainerColor,
            labelColor = unselectedContentColor,
            iconColor = selectedContentColor,

        )
    )
}