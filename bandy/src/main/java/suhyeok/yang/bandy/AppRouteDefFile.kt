package suhyeok.yang.bandy

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
    data object RecruitScreen : MainScreenRoute

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
    data class ProfileUpdateScreen(var userId: String = "") : NestedScreenRoute

    @Serializable
    data class PostingHistoryScreen(var userId: String = "") : NestedScreenRoute

    @Serializable
    data class ManageBandScreen(var bandId: String = "") : NestedScreenRoute

    @Serializable
    data class NotificationScreen(var userId: String = "") : NestedScreenRoute

    @Serializable
    data object CreateBandScreen : NestedScreenRoute

    @Serializable
    data object CreateRecruitingMemberScreen : NestedScreenRoute

    @Serializable
    data class ChatRoomScreen(var chatRoomId: String ="") : NestedScreenRoute
}