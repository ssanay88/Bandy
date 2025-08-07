package suhyeok.yang.shared.common.util

import com.yang.business.enums.AgeGroup
import com.yang.business.enums.Gender
import com.yang.business.enums.Instrument
import com.yang.business.enums.SkillLevel

/**
 * Instrument
 */
const val GUITAR_LABEL = "기타"
const val BASS_LABEL = "베이스"
const val DRUM_LABEL = "드럼"
const val MIXING_LABEL = "믹싱"
const val VOCAL_LABEL = "보컬"
const val PRODUCE_LABEL = "프로듀싱"
const val KEYBOARD_LABEL = "키보드"
const val NOTHING_LABEL = "미선택"

fun Instrument.toStr(): String =
    when (this) {
        Instrument.GUITAR -> GUITAR_LABEL
        Instrument.BASS -> BASS_LABEL
        Instrument.DRUM -> DRUM_LABEL
        Instrument.MIXING -> MIXING_LABEL
        Instrument.VOCAL -> VOCAL_LABEL
        Instrument.PRODUCE -> PRODUCE_LABEL
        Instrument.KEYBOARD -> KEYBOARD_LABEL
        else -> NOTHING_LABEL
    }

fun String.toInstrument(): Instrument =
    when (this) {
        GUITAR_LABEL -> Instrument.GUITAR
        BASS_LABEL -> Instrument.BASS
        DRUM_LABEL -> Instrument.DRUM
        MIXING_LABEL -> Instrument.MIXING
        VOCAL_LABEL -> Instrument.VOCAL
        PRODUCE_LABEL -> Instrument.PRODUCE
        KEYBOARD_LABEL -> Instrument.KEYBOARD
        else -> Instrument.NOTHING
    }


/**
 * SkillLevel
 */
const val BEGINNER_LABEL = "초보자"
const val JUNIOR_LABEL = "초급"
const val INTERMEDIATE_LABEL = "중급"
const val ADVANCED_LABEL = "고급"
const val EXPERT_LABEL = "전문가"

fun SkillLevel.toStr(): String =
    when (this) {
        SkillLevel.BEGINNER -> BEGINNER_LABEL
        SkillLevel.JUNIOR -> JUNIOR_LABEL
        SkillLevel.INTERMEDIATE -> INTERMEDIATE_LABEL
        SkillLevel.ADVANCED -> ADVANCED_LABEL
        SkillLevel.EXPERT -> EXPERT_LABEL
    }

fun String.toSkillLevel(): SkillLevel =
    when (this) {
        BEGINNER_LABEL -> SkillLevel.BEGINNER
        JUNIOR_LABEL -> SkillLevel.JUNIOR
        INTERMEDIATE_LABEL -> SkillLevel.INTERMEDIATE
        ADVANCED_LABEL -> SkillLevel.ADVANCED
        EXPERT_LABEL -> SkillLevel.EXPERT
        else -> SkillLevel.BEGINNER
    }

/**
 * AgeGroup
 */
const val TEENAGERS_LABEL = "10대"
const val TWENTIES_LABEL = "20대"
const val THIRTIES_LABEL = "30대"
const val FORTIES_LABEL = "40대"
const val OVER_FIFTIE_LABEL = "50대 이상"
const val ALL_AGES_LABEL = "전연령"

fun AgeGroup.toStr(): String =
    when (this) {
        AgeGroup.TEENAGERS -> TEENAGERS_LABEL
        AgeGroup.TWENTIES -> TWENTIES_LABEL
        AgeGroup.THIRTIES -> THIRTIES_LABEL
        AgeGroup.FORTIES -> FORTIES_LABEL
        AgeGroup.OVER_FIFTIE -> OVER_FIFTIE_LABEL
        AgeGroup.ALL_AGES -> ALL_AGES_LABEL
    }

fun List<AgeGroup>.toStr(): String =
    this.joinToString(separator = ", ") { it.toStr() }

fun String.toAgeGroup(): AgeGroup =
    when (this) {
        TEENAGERS_LABEL -> AgeGroup.TEENAGERS
        TWENTIES_LABEL -> AgeGroup.TWENTIES
        THIRTIES_LABEL -> AgeGroup.THIRTIES
        FORTIES_LABEL -> AgeGroup.FORTIES
        OVER_FIFTIE_LABEL -> AgeGroup.OVER_FIFTIE
        else -> AgeGroup.ALL_AGES
    }

/**
 * Gender
 */
const val ALL_LABEL = "상관 없음"
const val MALE_LABEL = "남성"
const val FEMALE_LABEL = "여성"

fun Gender.toStr(): String =
    when (this) {
        Gender.ALL -> ALL_LABEL
        Gender.MALE -> MALE_LABEL
        Gender.FEMALE -> FEMALE_LABEL
    }