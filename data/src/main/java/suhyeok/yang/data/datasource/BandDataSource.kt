package suhyeok.yang.data.datasource

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Band

interface BandDataSource {
    suspend fun createBand(newBand: Band): DataResourceResult<Unit>
    suspend fun readBand(bandId: String): DataResourceResult<Band>
    suspend fun readPopularBand(): DataResourceResult<List<Band>>
    suspend fun readRecruitingBand(): DataResourceResult<List<Band>>
    suspend fun readBandList(): DataResourceResult<List<Band>>
    suspend fun updateBand(updatedBand: Band): DataResourceResult<Unit>
    suspend fun deleteBand(bandId: String): DataResourceResult<Unit>
}