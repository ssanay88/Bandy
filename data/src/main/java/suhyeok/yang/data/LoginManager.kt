package suhyeok.yang.data

import android.content.Context
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.yang.business.common.DataResourceResult
import com.yang.business.model.LoginResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

object LoginManager {

    private val _loginResult = MutableStateFlow<DataResourceResult<LoginResponse>>(
        DataResourceResult.Uninitialized)
    val loginResult = _loginResult.asStateFlow()

    private val oauthLoginCallback = object : OAuthLoginCallback {
        override fun onSuccess() {
            _loginResult.value = DataResourceResult.Loading
            _loginResult.value = DataResourceResult.Success(LoginResponse(
                accessToken = NaverIdLoginSDK.getAccessToken() ?: "",
                refreshToken = NaverIdLoginSDK.getRefreshToken() ?: ""
            ))
        }

        override fun onFailure(httpStatus: Int, message: String) {
            _loginResult.value = DataResourceResult.Failure(Exception(message))
        }

        override fun onError(errorCode: Int, message: String) {
            _loginResult.value = DataResourceResult.Failure(Exception(message))
        }
    }

    fun naverLogin(context: Context) {
        NaverIdLoginSDK.authenticate(context, oauthLoginCallback)
    }

}