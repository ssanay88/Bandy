package suhyeok.yang.feature.ui.recruitmember

sealed class ValidationResult {
    object Success : ValidationResult()
    object InstrumentUnselected : ValidationResult()
    object PostingTitleEmpty : ValidationResult()
    object PostingContentEmpty : ValidationResult()
}