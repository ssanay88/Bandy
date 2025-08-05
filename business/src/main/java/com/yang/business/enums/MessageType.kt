package com.yang.business.enums

// 메시지 타입을 위한 Enum
enum class MessageType {
    TEXT,       // 일반 텍스트 메시지
    IMAGE,      // 이미지 메시지
    FILE,       // 파일 메시지
    VOICE,      // 음성 메시지 (추가 고려)
    SYSTEM      // 시스템 메시지 (e.g., "XX님이 입장했습니다.")
}