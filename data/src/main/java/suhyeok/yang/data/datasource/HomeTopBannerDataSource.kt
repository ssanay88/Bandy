package suhyeok.yang.data.datasource

import com.yang.business.common.DataResourceResult
import com.yang.business.model.HomeTopBanner

interface HomeTopBannerDataSource {
    suspend fun readHomeTopBanner(): DataResourceResult<List<HomeTopBanner>>
}