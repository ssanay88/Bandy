package suhyeok.yang.shared.common.util

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

/**
 * LocalDateTime을 기준으로 "방금 전", "N분 전" 등으로 시간을 포맷하는 함수
 *
 * @param dateTime 포스팅 또는 기준이 되는 LocalDateTime 객체
 * @return 포맷된 시간 문자열
 */
object DateTimeUtils {

    private val isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    // LocalDateTime To ISO String
    fun toIsoString(localDateTime: LocalDateTime) = localDateTime.format(isoFormatter)

    // ISO String to LocalDateTime
    fun toLocalDateTime(isoString: String) = LocalDateTime.parse(isoString, isoFormatter)

    // ISO String to LocalDate
    fun toLocalDate(isoString: String) = LocalDate.parse(isoString, isoFormatter)

    fun formatTimeAgo(dateTime: LocalDateTime): String {
        // 현재 시간을 가져옴 (시간대 정보 없음)
        val currentTime = LocalDateTime.now()

        // 두 LocalDateTime 객체 간의 시간 차이를 Duration으로 계산
        // Duration은 나노초 단위까지 정확하게 계산하지만, 여기서는 초/분/시간 등으로 변환
        val duration = Duration.between(dateTime, currentTime)

        return when {
            // 1분 미만
            duration.toMinutes() < 1 -> {
                val seconds = duration.toSeconds()
                if (seconds <= 20) "방금 전"
                else "${seconds}초 전"
            }
            // 1시간 미만
            duration.toHours() < 1 -> {
                val minutes = duration.toMinutes()
                "${minutes}분 전"
            }
            // 1일 미만
            duration.toDays() < 1 -> {
                val hours = duration.toHours()
                "${hours}시간 전"
            }
            // 7일 미만
            duration.toDays() < 7 -> {
                val days = duration.toDays()
                "${days}일 전"
            }
            // 30일 미만 (약 1달 미만)
            duration.toDays() < 30 -> { // 약 한 달을 30일로 가정
                val weeks = duration.toDays() / 7
                "${weeks}주 전"
            }
            // 365일 미만 (1년 미만)
            duration.toDays() < 365 -> { // 약 1년을 365일로 가정
                val months = duration.toDays() / 30 // 약 한 달을 30일로 계산
                "${months}달 전"
            }
            // 1년 이상
            else -> {
                val years = duration.toDays() / 365 // 약 1년을 365일로 계산
                "${years}년 전"
            }
        }
    }

    /**
     * 두 LocalDateTime 객체 사이의 일(day) 수 차이를 계산하는 함수
     *
     * @param startDateTime 시작 LocalDateTime
     * @param endDateTime 종료 LocalDateTime
     * @return 시작일과 종료일 사이의 일 수
     */
    fun calculateDaysBetweenDates(startDateTime: LocalDateTime, endDateTime: LocalDateTime): Long {
        // Duration.between()은 두 시간점 사이의 정확한 시간 간격을 계산합니다.
        // 여기서는 일(day) 단위로 변환합니다.
        return Duration.between(startDateTime, endDateTime).toDays()
    }

    /**
     * 년, 월, 일을 입력받아 LocalDateTime 객체를 생성하는 함수
     * 시간은 00:00:00으로 설정됩니다.
     *
     * @param year 년 (예: 2023)
     * @param month 월 (1-12, 예: 10월은 10)
     * @param day 일 (1-31)
     * @return 생성된 LocalDateTime 객체
     */
    fun createDateFromYMD(year: Int, month: Int, day: Int): LocalDateTime {
        // LocalDateTime.of()를 사용하여 간결하게 생성
        return LocalDateTime.of(year, month, day, 0, 0, 0) // 시간은 00:00:00으로 설정
    }

    /**
     * LocalDateTime 객체를 "yyyy.MM.dd" 형식의 문자열로 포맷하는 함수
     *
     * @param dateTime 포맷할 LocalDateTime 객체
     * @return 포맷된 날짜 문자열
     */
    fun formatDateToYYYYMMDD(dateTime: LocalDateTime): String {
        // DateTimeFormatter를 사용하여 포맷 정의
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.getDefault())
        return dateTime.format(formatter) // LocalDateTime의 format 메서드 사용
    }

    /**
     * 년, 월, 일, 시, 분, 초를 입력받아 LocalDateTime 객체를 생성하는 헬퍼 함수
     *
     * @param year 년
     * @param month 월 (1-12)
     * @param day 일
     * @param hour 시 (0-23)
     * @param minute 분 (0-59)
     * @param second 초 (0-59)
     * @return 생성된 LocalDateTime 객체
     */
    fun createDateFromYMDHMS(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, second: Int = 0): LocalDateTime {
        return LocalDateTime.of(year, month, day, hour, minute, second)
    }
}
