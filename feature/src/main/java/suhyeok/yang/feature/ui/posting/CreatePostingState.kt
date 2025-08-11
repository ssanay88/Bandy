package suhyeok.yang.feature.ui.posting

sealed class CreatePostingState {
    object DummyState : CreatePostingState()
    object Complete : CreatePostingState()
    data class Fail(val failMessage: String?) : CreatePostingState()
    object Uploading : CreatePostingState()
}