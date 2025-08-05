package com.yang.business.usecase.posting

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Posting
import com.yang.business.repository.PostingRepository
import kotlinx.coroutines.flow.Flow

class ReadPostingUseCase(val postingRepository: PostingRepository) {
    suspend operator fun invoke() : Flow<DataResourceResult<List<Posting>>> {
        return postingRepository.readPosting()
    }
}