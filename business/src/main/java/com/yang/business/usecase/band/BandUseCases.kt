package com.yang.business.usecase.band

data class BandUseCases(
    val createBand: CreateBandUseCase,
    val readBand: ReadBandUseCase,
    val readPopularBand: ReadPopularBandUseCase,
    val readRecruitingBand: ReadRecruitingBandUseCase,
    val readBandList: ReadBandListUseCase,
    val updateBand: UpdateBandUseCase,
    val deleteBand: DeleteBandUseCase
)