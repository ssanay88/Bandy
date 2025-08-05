package com.yang.business.model

data class Region(
    val sido: String,      // 시/도 (예: "서울특별시", "부산광역시")
    val sigungu: String,   // 시/군/구 (예: "관악구", "수영구")
) {
    override fun toString(): String =  if (sido == "all") "전국" else "$sido $sigungu"
}
