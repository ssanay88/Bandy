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
    val startDate: LocalDate,
    var leaderId: String,
    var isRecruiting: Boolean,
    var preferredGenres: List<String>,
    var viewCount: Int
    // var bandGallery TODO 갤러리 기능 추가
)
