package suhyeok.yang.data.remote.auth

import android.content.Context
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import com.yang.business.common.DataResourceResult
import com.yang.business.model.LoginResponse
import suhyeok.yang.data.datasource.AuthDataSource

class AuthDataSourceImpl(): AuthDataSource {
    override suspend fun loginWithNaver(context: Context): DataResourceResult<LoginResponse> = runCatching {
        NaverIdLoginSDK.authenticate(context, object : OAuthLoginCallback {
            override fun onSuccess() {
                NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                    override fun onSuccess(result: NidProfileResponse) {
                        DataResourceResult.Success(
                            LoginResponse(
                                profileId = result.profile?.id ?: "",
                                accessToken = NaverIdLoginSDK.getAccessToken() ?: "",
                                refreshToken = NaverIdLoginSDK.getRefreshToken() ?: ""
                            )
                        )
                    }

                    override fun onError(errorCode: Int, message: String) {
                        DataResourceResult.Failure(Throwable("$errorCode $message"))
                    }

                    override fun onFailure(httpStatus: Int, message: String) {
                        DataResourceResult.Failure(Throwable(message))
                    }

                })
            }

            override fun onError(errorCode: Int, message: String) {
                DataResourceResult.Failure(Throwable("$errorCode $message"))
            }

            override fun onFailure(httpStatus: Int, message: String) {
                DataResourceResult.Failure(Throwable(message))
            }
        })
        DataResourceResult.Loading
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

}