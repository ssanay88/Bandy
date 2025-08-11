package suhyeok.yang.bandy

import com.yang.business.enums.RecruitScreenTab
import kotlinx.serialization.Serializable

/**
 * Main screen route
 */
@Serializable
sealed interface MainScreenRoute {

    // MainActivity
    @Serializable
    data object HomeScreen : MainScreenRoute

    @Serializable
    data class RecruitScreen(val currentTab: RecruitScreenTab = RecruitScreenTab.BAND_RECRUIT_TAB) : MainScreenRoute

    @Serializable
    data object MyBandScreen : MainScreenRoute

    @Serializable
    data object ChatScreen : MainScreenRoute

    @Serializable
    data object ProfileScreen : MainScreenRoute
}

@Serializable
sealed interface SubScreenRoute {

    // LoginActivity
    @Serializable
    data object LoginScreen : SubScreenRoute

    @Serializable
    data class ProfileRegScreen(var newUserId: String = "") : SubScreenRoute

}

/**
 * Nested Screen Route
 */
@Serializable
sealed interface NestedScreenRoute {
    @Serializable
    data class BandInfoScreen(var bandId: String = "") : NestedScreenRoute

    @Serializable
    data class PostingDetailScreen(var postingId: String = "") : NestedScreenRoute

    @Serializable
    data class RecruitingMemberScreen(var recruitingPostingId: String = "") : NestedScreenRoute

    @Serializable
    object ProfileUpdateScreen : NestedScreenRoute

    @Serializable
    object PostingHistoryScreen : NestedScreenRoute

    @Serializable
    object ManageBandScreen : NestedScreenRoute

    @Serializable
    object NotificationScreen : NestedScreenRoute

    @Serializable
    data object CreateBandScreen : NestedScreenRoute

    @Serializable
    data object CreateRecruitingMemberScreen : NestedScreenRoute

    @Serializable
    data class ChatRoomScreen(var chatRoomId: String ="") : NestedScreenRoute

    @Serializable
    object CreatePostingScreen : NestedScreenRoute
}