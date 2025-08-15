package suhyeok.yang.data.repository

import android.content.Context
import com.yang.business.common.DataResourceResult
import com.yang.business.model.LoginResponse
import com.yang.business.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import suhyeok.yang.data.datasource.AuthDataSource

class AuthRepositoryImpl(
    private val authDataSource: AuthDataSource,
    private val context: Context
): AuthRepository {
    override suspend fun loginWithNaver(): Flow<DataResourceResult<LoginResponse>> = flow {
        authDataSource.loginWithNaver(context)
    }
}