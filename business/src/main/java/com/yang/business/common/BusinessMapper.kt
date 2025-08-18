package com.yang.business.common

import com.yang.business.model.User
import com.yang.business.model.UserSession

fun User.toUserSession() = UserSession(
    isLogged = true,
    userId = this.userId,
    userName = this.userName,
    userNickname = this.nickName,
    userProfileImage = this.profileImageUrl,
    userDescription = this.userDescription,
    userInstrument = this.instrument,
    userRegion = this.region,
    isLeader = this.isLeader,
    isBand = this.bandId.isNotEmpty(),
    bandId = this.bandId
)