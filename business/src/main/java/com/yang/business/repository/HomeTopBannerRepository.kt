package com.yang.business.repository

import com.yang.business.common.DataResourceResult
import com.yang.business.model.HomeTopBanner
import kotlinx.coroutines.flow.Flow

interface HomeTopBannerRepository {
    suspend fun readHomeTopBanner(): Flow<DataResourceResult<List<HomeTopBanner>>>
}