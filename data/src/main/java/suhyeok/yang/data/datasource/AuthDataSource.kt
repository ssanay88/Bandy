package suhyeok.yang.data.datasource

import android.content.Context
import com.yang.business.common.DataResourceResult
import com.yang.business.model.LoginResponse

interface AuthDataSource {
    suspend fun loginWithNaver(context: Context): DataResourceResult<LoginResponse>
}