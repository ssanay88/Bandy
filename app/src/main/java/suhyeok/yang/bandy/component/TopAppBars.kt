@file:OptIn(ExperimentalMaterial3Api::class)

package suhyeok.yang.bandy.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import suhyeok.yang.bandy.R
import suhyeok.yang.bandy.ui.theme.Red
import suhyeok.yang.bandy.ui.theme.White
import suhyeok.yang.bandy.utils.toSp


@Composable
fun HomeScreenTopAppBar(
    modifier: Modifier = Modifier
) {
    BaseAppBar()
}

@Composable
fun RecruitScreenTopAppBar(
    modifier: Modifier = Modifier
) {
    BaseAppBar()
}

@Composable
fun MyBandScreenTopAppBar(
    modifier: Modifier = Modifier
) {
    BaseAppBar()
}

@Composable
fun ProfileScreenTopAppBar(
    modifier: Modifier = Modifier
) {
    BaseAppBar()
}

@Composable
fun BaseAppBar(
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = { StartAppLogoIcon() },
    actions: @Composable RowScope.() -> Unit = { EndBadgeIconsSection() },
    scrollBehavior: TopAppBarScrollBehavior? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()
) {
    TopAppBar(
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior,
        colors = colors
    )
}

@Composable
fun StartAppLogoIcon() {
    Icon(
        painter = painterResource(R.drawable.ic_bandy),
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = Modifier.padding(start = dimensionResource(R.dimen.start_app_logo_icon_start_padding))
    )
}

@Composable
fun EndBadgeIconsSection() {
    Row(
        modifier = Modifier
            .padding(end = dimensionResource(R.dimen.end_badge_icons_section_padding))
            .padding(vertical = dimensionResource(R.dimen.end_badge_icons_section_padding))
    ) {
        BadgeIcon(
            modifier = Modifier,
            iconId = R.drawable.ic_outline_chat,
            contentDescription = stringResource(R.string.top_app_bar_chat_icon_descript)
        )

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.end_badge_icons_section_spacer_padding)))

        BadgeIcon(
            modifier = Modifier,
            iconId = R.drawable.ic_outline_notification,
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
    var itemCount by remember { mutableStateOf(2) }

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
            contentDescription = contentDescription
        )
    }
}

@Preview
@Composable
fun HomeScreenTopAppBarPreview() {
    HomeScreenTopAppBar()
}