package suhyeok.yang.shared.common.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

fun formatTimeAgo(date: Date): String {
    val currentTime = System.currentTimeMillis() // 현재 시간을 밀리초 단위로 가져옴
    val postTime = date.time // 포스팅 시간을 밀리초 단위로 가져옴

    val diffMillis = currentTime - postTime // 현재 시간과 포스팅 시간의 차이 (밀리초)

    return when {
        // 1분 미만 (60초 미만)
        diffMillis < TimeUnit.MINUTES.toMillis(1) -> {
            val seconds = TimeUnit.MILLISECONDS.toSeconds(diffMillis)
            if (seconds <= 20) "방금 전" // 0초 또는 음수일 경우
            else "${seconds}초 전"
        }
        // 1시간 미만 (60분 미만)
        diffMillis < TimeUnit.HOURS.toMillis(1) -> {
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
            "${minutes}분 전"
        }
        // 1일 미만 (24시간 미만)
        diffMillis < TimeUnit.DAYS.toMillis(1) -> {
            val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
            "${hours}시간 전"
        }
        // 7일 미만 (1주 미만)
        diffMillis < TimeUnit.DAYS.toMillis(7) -> {
            val days = TimeUnit.MILLISECONDS.toDays(diffMillis)
            "${days}일 전"
        }
        // 30일 미만 (약 1달 미만)
        diffMillis < TimeUnit.DAYS.toMillis(30) -> { // 약 한 달을 30일로 가정
            val weeks = TimeUnit.MILLISECONDS.toDays(diffMillis) / 7
            "${weeks}주 전"
        }
        // 365일 미만 (1년 미만)
        diffMillis < TimeUnit.DAYS.toMillis(365) -> { // 약 1년을 365일로 가정
            val months = TimeUnit.MILLISECONDS.toDays(diffMillis) / 30 // 약 한 달을 30일로 계산
            "${months}달 전"
        }
        // 1년 이상
        else -> {
            val years = TimeUnit.MILLISECONDS.toDays(diffMillis) / 365 // 약 1년을 365일로 계산
            "${years}년 전"
        }
    }
}

fun calculateDaysBetweenDates(startDate: Date, endDate: Date): Long {
    val diffInMillies = endDate.time - startDate.time

    return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
}

fun createDateFromYMD(year: Int, month: Int, day: Int): Date {
    val calendar = Calendar.getInstance()

    calendar.set(year, month - 1, day)

    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)

    return calendar.time
}

fun formatDateToYYYYMMDD(date: Date): String {
    val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    return formatter.format(date)
}

// 년, 월, 일, 시, 분, 초를 입력받아 Date 객체를 생성하는 헬퍼 함수
fun createDateFromYMDHMS(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, second: Int = 0): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, day, hour, minute, second)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.time
}