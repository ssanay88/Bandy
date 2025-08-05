package suhyeok.yang.data.remote.dto

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class HomeTopBannerDTO(
    val _bannerImageUrl: String = "",
    val _bannerTitle: String = "",
    val _bannerDescription: String = "",
    val _linkUrl: String = ""
)
