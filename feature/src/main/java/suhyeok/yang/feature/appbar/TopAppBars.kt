@file:OptIn(ExperimentalMaterial3Api::class)

package suhyeok.yang.feature.appbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import com.yang.business.model.TopBarItem
import com.yang.business.enums.TopBarType
import suhyeok.yang.feature.R
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.R as SharedR
import suhyeok.yang.shared.common.util.toSp
import suhyeok.yang.shared.ui.theme.Red
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.Tertiary
import suhyeok.yang.shared.ui.theme.White

@Composable
fun TopAppBar(
    topBarItem: TopBarItem,
    onLogoIconClick: () -> Unit = {},
    onChatIconClick: () -> Unit = {},
    onNotiIconClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
) {
    when (topBarItem.topBarType) {
        TopBarType.DEFAULT -> {
            DefaultAppBar(
                onLogoIconClick = onLogoIconClick,
                onChatIconClick = onChatIconClick,
                onNotiIconClick = onNotiIconClick
            )
        }

        TopBarType.BACK_ONLY -> {
            BackOnlyAppBar(
                onBackClick = onBackClick
            )
        }

        TopBarType.BACK_WITH_TITLE -> {
            BackWithTitleAppBar(
                titleResId = topBarItem.titleResId,
                onBackClick = onBackClick
            )
        }
    }
}

@Preview
@Composable
fun BaseAppBar(
    title: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = { },
    actions: @Composable RowScope.() -> Unit = { },
    expandedHeight: Dp = TopAppBarDefaults.TopAppBarExpandedHeight,
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    Column {
        CenterAlignedTopAppBar(
            title = title,
            modifier = modifier,
            navigationIcon = navigationIcon,
            actions = actions,
            expandedHeight = expandedHeight,
            windowInsets = windowInsets,
            scrollBehavior = scrollBehavior,
            colors = colors
        )
        TopAppBarDivider()
    }
}

@Composable
fun DefaultAppBar(
    onLogoIconClick: () -> Unit,
    onChatIconClick: () -> Unit,
    onNotiIconClick: () -> Unit
) {
    BaseAppBar(
        navigationIcon = { LeftAppLogoIcon(onLogoIconClick) },
        actions = { RightBadgeIconsSection(onChatIconClick, onNotiIconClick) },
    )
}

@Composable
fun BackOnlyAppBar(
    onBackClick: () -> Unit
) {
    BaseAppBar(
        navigationIcon = {
            Icon(
                modifier = Modifier.throttleClick { onBackClick() },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.top_app_bar_back_icon_descript)
            )
        }
    )
}

@Composable
fun BackWithTitleAppBar(
    titleResId: Int,
    onBackClick: () -> Unit
) {
    BaseAppBar(
        title = {
            Text(
                text = stringResource(titleResId),
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            Icon(
                modifier = Modifier.throttleClick { onBackClick() },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.top_app_bar_back_icon_descript)
            )
        }
    )
}

@Composable
fun TopAppBarDivider() {
    Spacer(
        modifier = Modifier.fillMaxWidth()
            .height(dimensionResource(R.dimen.top_app_bar_divider_height)).background(Tertiary)
    )
}

@Composable
fun LeftAppLogoIcon(
    onLogoIconClick: () -> Unit
) {
    Icon(
        painter = painterResource(R.drawable.bandy_logo_primary_color),
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = Modifier.padding(start = dimensionResource(R.dimen.start_app_logo_icon_start_padding)).throttleClick { onLogoIconClick() }
    )
}

@Composable
fun RightBadgeIconsSection(
    onChatIconClick: () -> Unit,
    onNotiIconClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(end = dimensionResource(R.dimen.end_badge_icons_section_padding))
            .padding(vertical = dimensionResource(R.dimen.end_badge_icons_section_padding))
    ) {
        BadgeIcon(
            modifier = Modifier.throttleClick { onChatIconClick() },
            iconId = SharedR.drawable.ic_outline_chat,
            contentDescription = stringResource(R.string.top_app_bar_chat_icon_descript)
        )

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.end_badge_icons_section_spacer_padding)))

        BadgeIcon(
            modifier = Modifier.throttleClick { onNotiIconClick() },
            iconId = SharedR.drawable.ic_outline_notification,
            contentDescription = stringResource(R.string.top_app_bar_noti_icon_descript)
        )
    }
}

@Composable
fun BadgeIcon(
    modifier: Modifier = Modifier,
    iconId: Int,
    contentDescription: String? = null
) {
    var itemCount by remember { mutableStateOf(0) }

    BadgedBox(
        modifier = modifier,
        badge = {
            if (itemCount > 0) {
                Badge(
                    containerColor = Red,
                    contentColor = White,
                    modifier = Modifier.size(dimensionResource(R.dimen.badge_icon_size))
                ) {
                    Text(
                        text = "$itemCount",
                        fontSize = dimensionResource(R.dimen.badge_icon_text_font_size).toSp()
                    )
                }
            }
        }
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = contentDescription,
            modifier = Modifier.size(dimensionResource(R.dimen.end_badge_icon_size))
        )
    }
}