package com.yang.business.usecase.recruitposting

import com.yang.business.common.DataResourceResult
import com.yang.business.model.RecruitPosting
import com.yang.business.repository.RecruitPostingRepository
import kotlinx.coroutines.flow.Flow

class CreateRecruitPostingUseCase(val recruitPostingRepository: RecruitPostingRepository) {
    suspend operator fun invoke(newPosting: RecruitPosting): Flow<DataResourceResult<Unit>> {
        return recruitPostingRepository.createRecruitPosting(newPosting)
    }
}