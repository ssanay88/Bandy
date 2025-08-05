package com.yang.business.model

data class LoginResponse(
    val profileId: String = "",
    val accessToken: String = "",
    val refreshToken: String = ""
)
