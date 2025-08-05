package suhyeok.yang.data.remote.dto

import com.google.firebase.firestore.IgnoreExtraProperties
import com.yang.business.model.Region
import com.yang.business.model.User
import java.time.LocalDate

@IgnoreExtraProperties
data class BandDTO(
    val _bandId: String = "",
    var _bandName: String = "",
    var _bandProfileImageUrl: String = "",
    var _coverImageUrl: String = "",
    var _bandDescription: String = "",
    var _members: List<UserDTO> = listOf(),
    var _region: String = "",
    val _startDate: String = "",
    var _leaderId: String = "",
    var _isRecruiting: Boolean = false,
    var _preferredGenres: List<String> = listOf(),
    var _viewCount: Int = 0
    // var _bandGallery TODO 갤러리 기능 추가
)