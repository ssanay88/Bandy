package com.yang.business.usecase.posting

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Posting
import com.yang.business.repository.PostingRepository
import kotlinx.coroutines.flow.Flow

class ReadPostingDetailUseCase(val postingRepository: PostingRepository) {
    suspend operator fun invoke(postingId: String) : Flow<DataResourceResult<Posting>> {
        return postingRepository.readPostingDetail(postingId)
    }
}