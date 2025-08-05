package suhyeok.yang.bandy.main_nav

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import suhyeok.yang.bandy.R
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.TextGray
import suhyeok.yang.shared.ui.theme.White

@Composable
fun BottomNavBar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = White
    ) {
        BottomNavItem().getBottomNavItems().forEachIndexed { idx, navItem ->
            NavigationBarItem(
                selected = currentDestination?.route?.contains(navItem.route) == true,
                onClick = {
                    navController.navigate(navItem.destination) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(navItem.tabIcon),
                        contentDescription = stringResource(navItem.tabTitleResId),
                        modifier = Modifier.size(dimensionResource(R.dimen.bottom_nav_bar_icon_size))
                    )
                },
                label = {
                    Text(
                        text = stringResource(navItem.tabTitleResId),
                        fontFamily = SuitFontFamily,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Primary,
                    selectedTextColor = Primary,
                    unselectedIconColor = TextGray,
                    unselectedTextColor = TextGray,
                    indicatorColor = Color.Transparent
                ),
                interactionSource = NoRippleInteractionSource
            )
        }
    }
}

internal object NoRippleInteractionSource : MutableInteractionSource {
    override suspend fun emit(interaction: Interaction) = Unit

    override fun tryEmit(interaction: Interaction): Boolean = true

    override val interactions: Flow<Interaction>
        get() = emptyFlow<Interaction>()

}