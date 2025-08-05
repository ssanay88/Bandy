package com.yang.business.usecase.recruitposting

import com.yang.business.common.DataResourceResult
import com.yang.business.model.RecruitPosting
import com.yang.business.repository.RecruitPostingRepository
import kotlinx.coroutines.flow.Flow

class ReadRecruitPostingUseCase(val recruitPostingRepository: RecruitPostingRepository) {
    suspend operator fun invoke(recruitPostingId: String): Flow<DataResourceResult<RecruitPosting>> {
        return recruitPostingRepository.readRecruitPosting(recruitPostingId)
    }
}