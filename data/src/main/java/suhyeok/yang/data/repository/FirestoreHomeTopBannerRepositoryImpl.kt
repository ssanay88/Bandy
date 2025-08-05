package suhyeok.yang.data.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.HomeTopBanner
import com.yang.business.repository.HomeTopBannerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import suhyeok.yang.data.datasource.HomeTopBannerDataSource

class FirestoreHomeTopBannerRepositoryImpl(
    val targetDataSource: HomeTopBannerDataSource
): HomeTopBannerRepository {
    override suspend fun readHomeTopBanner(): Flow<DataResourceResult<List<HomeTopBanner>>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.readHomeTopBanner())
    }.catch {
        emit(DataResourceResult.Failure(it))
    }
}