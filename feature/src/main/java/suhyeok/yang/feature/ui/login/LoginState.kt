package suhyeok.yang.feature.ui.login

sealed class LoginState {
    object Init: LoginState()
    object FirstLogin: LoginState()
    object HasProfile: LoginState()
    object Loading: LoginState()
    object Failure: LoginState()
}