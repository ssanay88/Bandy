package com.yang.business.model

data class Region(
    val sido: String = "서울특별시",      // 시/도 (예: "서울특별시", "부산광역시")
    val sigungu: String = "전체",   // 시/군/구 (예: "관악구", "수영구")
) {
    override fun toString(): String = "$sido $sigungu"
}
