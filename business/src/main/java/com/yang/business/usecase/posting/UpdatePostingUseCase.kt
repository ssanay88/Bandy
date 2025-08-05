package com.yang.business.usecase.posting

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Posting
import com.yang.business.repository.PostingRepository
import kotlinx.coroutines.flow.Flow

class UpdatePostingUseCase(val postingRepository: PostingRepository) {
    suspend operator fun invoke(updatedPosting: Posting): Flow<DataResourceResult<Unit>> {
        return postingRepository.updatePosting(updatedPosting)
    }
}