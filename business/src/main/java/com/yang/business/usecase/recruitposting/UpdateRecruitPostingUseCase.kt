package com.yang.business.usecase.recruitposting

import com.yang.business.common.DataResourceResult
import com.yang.business.model.RecruitPosting
import com.yang.business.repository.RecruitPostingRepository
import kotlinx.coroutines.flow.Flow

class UpdateRecruitPostingUseCase(val recruitPostingRepository: RecruitPostingRepository) {
    suspend operator fun invoke(updatedRecruitPosting: RecruitPosting): Flow<DataResourceResult<Unit>> {
        return recruitPostingRepository.updateRecruitPosting(updatedRecruitPosting)
    }
}