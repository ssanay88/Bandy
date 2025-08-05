package com.yang.business.usecase.hometopbanner

import com.yang.business.common.DataResourceResult
import com.yang.business.model.HomeTopBanner
import com.yang.business.repository.HomeTopBannerRepository
import kotlinx.coroutines.flow.Flow

class ReadHomeTopBannerUseCase(val homeTopBannerRepository: HomeTopBannerRepository) {
    suspend operator fun invoke(): Flow<DataResourceResult<List<HomeTopBanner>>> {
        return homeTopBannerRepository.readHomeTopBanner()
    }
}