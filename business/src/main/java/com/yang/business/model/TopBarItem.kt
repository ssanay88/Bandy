package com.yang.business.model

import com.yang.business.enums.TopBarType

data class TopBarItem(
    val topBarType: TopBarType = TopBarType.DEFAULT,
    val titleResId: Int = 0
)
