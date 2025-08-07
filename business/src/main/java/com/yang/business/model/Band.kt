package com.yang.business.model

import java.time.LocalDate

data class Band(
    val bandId: String,
    var bandName: String,
    var bandProfileImageUrl: String,
    var coverImageUrl: String,
    var bandDescription: String,
    var members: List<User>,
    var region: Region,
    val startDate: LocalDate = LocalDate.now(),
    var leaderId: String,
    var isRecruiting: Boolean = false,
    var preferredGenres: List<String> = emptyList(),
    var viewCount: Int = 0,
    var youtubeLink: String = "",
    var instagramLink: String = "",
    var spotifyLink: String = ""
)
