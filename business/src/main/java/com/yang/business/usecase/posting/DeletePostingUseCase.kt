package com.yang.business.usecase.posting

import com.yang.business.common.DataResourceResult
import com.yang.business.repository.PostingRepository
import kotlinx.coroutines.flow.Flow

class DeletePostingUseCase(val postingRepository: PostingRepository) {
    suspend operator fun invoke(postingId: String): Flow<DataResourceResult<Unit>> {
        return postingRepository.deletePosting(postingId)
    }
}