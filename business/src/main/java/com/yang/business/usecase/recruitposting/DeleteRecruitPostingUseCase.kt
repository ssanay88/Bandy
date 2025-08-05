package com.yang.business.usecase.recruitposting

import com.yang.business.common.DataResourceResult
import com.yang.business.repository.RecruitPostingRepository
import kotlinx.coroutines.flow.Flow

class DeleteRecruitPostingUseCase(val recruitPostingRepository: RecruitPostingRepository) {
    suspend operator fun invoke(postingId: String) : Flow<DataResourceResult<Unit>> {
        return recruitPostingRepository.deleteRecruitPosting(postingId)
    }
}