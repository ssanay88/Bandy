package suhyeok.yang.bandy.nav_graph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val tabName: String = "",
    val tabIcon: ImageVector = Icons.Filled.Home,
    val tabRoute: String = ""
) {
    fun getBottomNavItems(): List<BottomNavItem> {
        return listOf(
            BottomNavItem(
                tabName = BottomNavTabName.HOME_TAB,
                tabIcon = Icons.Filled.Home,
                tabRoute = BandyDestinations.HOME_ROUTE
            ),
            BottomNavItem(
                tabName = BottomNavTabName.RECRUIT_TAB,
                tabIcon = Icons.Filled.Home,
                tabRoute = BandyDestinations.RECRUIT_ROUTE
            ),
            BottomNavItem(
                tabName = BottomNavTabName.MY_BAND_TAB,
                tabIcon = Icons.Filled.Home,
                tabRoute = BandyDestinations.MY_BAND_ROUTE
            ),
            BottomNavItem(
                tabName = BottomNavTabName.PROFILE_TAB,
                tabIcon = Icons.Filled.Home,
                tabRoute = BandyDestinations.PROFILE_ROUTE
            )
        )
    }
}
