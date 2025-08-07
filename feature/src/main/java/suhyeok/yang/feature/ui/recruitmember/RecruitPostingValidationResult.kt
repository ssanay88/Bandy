package suhyeok.yang.feature.ui.recruitmember

sealed class RecruitPostingValidationResult {
    object Success : RecruitPostingValidationResult()
    object InstrumentUnselected : RecruitPostingValidationResult()
    object PostingTitleEmpty : RecruitPostingValidationResult()
    object PostingContentEmpty : RecruitPostingValidationResult()
}