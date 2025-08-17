package suhyeok.yang.data.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Band
import com.yang.business.repository.BandRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import suhyeok.yang.data.datasource.BandDataSource
import javax.inject.Inject

class FirestoreBandRepositoryImpl @Inject constructor(val targetDataSource: BandDataSource): BandRepository {
    override suspend fun createBand(newBand: Band): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.createBand(newBand))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun readBand(bandId: String): Flow<DataResourceResult<Band>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.readBand(bandId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun readPopularBand(): Flow<DataResourceResult<List<Band>>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.readPopularBand())
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun readRecruitingBand(): Flow<DataResourceResult<List<Band>>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.readRecruitingBand())
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun readBandList(): Flow<DataResourceResult<List<Band>>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.readBandList())
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun updateBand(updatedBand: Band): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.updateBand(updatedBand))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

    override suspend fun deleteBand(bandId: String): Flow<DataResourceResult<Unit>> = flow {
        emit(DataResourceResult.Loading)
        emit(targetDataSource.deleteBand(bandId))
    }.catch {
        emit(DataResourceResult.Failure(it))
    }

}