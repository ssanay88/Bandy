package suhyeok.yang.feature.ui.profile

sealed class ProfileUpdateValidationResult {
    object Success : ProfileUpdateValidationResult()
    object InstrumentUnselected : ProfileUpdateValidationResult()
    object NicknameEmpty : ProfileUpdateValidationResult()
}