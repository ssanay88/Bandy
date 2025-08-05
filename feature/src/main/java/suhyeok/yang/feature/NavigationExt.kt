package suhyeok.yang.feature

import androidx.navigation.NavBackStackEntry
import com.yang.business.model.TopBarItem
import com.yang.business.enums.TopBarType
import suhyeok.yang.shared.R

val NavBackStackEntry.topBarAsRouteName: TopBarItem
    get() {
        // 현재 탭 바
        val routeName = destination.route ?: return TopBarItem()
        return when {
            // MainScreen
            routeName.contains("HomeScreen") -> {
                TopBarItem(titleResId = R.string.home_screen_title)
            }
            routeName.contains("RecruitScreen") -> {
                TopBarItem(titleResId = R.string.recruit_screen_title)
            }
            routeName.contains("MyBandScreen") -> {
                TopBarItem(titleResId = R.string.my_band_screen_title)
            }
            routeName.contains("ChatScreen") -> {
                TopBarItem(titleResId = R.string.chat_screen_title)
            }
            routeName.contains("ProfileScreen") -> {
                TopBarItem(titleResId = R.string.profile_screen_title)
            }

            // NestedScreen
            routeName.contains("BandInfoScreen") -> {
                TopBarItem(
                    topBarType = TopBarType.BACK_ONLY
                )
            }
            routeName.contains("PostingDetailScreen") -> {
                TopBarItem(
                    topBarType = TopBarType.BACK_WITH_TITLE,
                    titleResId = R.string.posting_detail_screen_title
                )
            }
            routeName.contains("RecruitingMemberDetailScreen") -> {
                TopBarItem(
                    topBarType = TopBarType.BACK_ONLY
                )
            }
            routeName.contains("ProfileUpdateScreen") -> {
                TopBarItem(
                    topBarType = TopBarType.BACK_WITH_TITLE,
                    titleResId = R.string.profile_update_screen_title
                )
            }
            routeName.contains("PostingHistoryScreen") -> {
                TopBarItem(
                    topBarType = TopBarType.BACK_WITH_TITLE,
                    titleResId = R.string.posting_history_screen_title
                )
            }
            routeName.contains("ManageBandScreen") -> {
                TopBarItem(
                    topBarType = TopBarType.BACK_WITH_TITLE,
                    titleResId = R.string.manage_band_screen_title
                )
            }
            routeName.contains("NotificationScreen") -> {
                TopBarItem(
                    topBarType = TopBarType.BACK_WITH_TITLE,
                    titleResId = R.string.notification_screen_title
                )
            }
            routeName.contains("CreateBandScreen") -> {
                TopBarItem(
                    topBarType = TopBarType.BACK_ONLY
                )
            }
            routeName.contains("CreateRecruitingMemberScreen") -> {
                TopBarItem(
                    topBarType = TopBarType.BACK_ONLY
                )
            }
            routeName.contains("ChatRoomScreen") -> {
                TopBarItem(
                    topBarType = TopBarType.BACK_ONLY
                )
            }

            else -> throw IllegalArgumentException("Unknown route name: $routeName")
        }
    }