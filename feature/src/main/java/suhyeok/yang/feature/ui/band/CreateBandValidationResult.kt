package suhyeok.yang.feature.ui.band

sealed class CreateBandValidationResult {
    object Success : CreateBandValidationResult()
    object BandNameEmpty : CreateBandValidationResult()
}