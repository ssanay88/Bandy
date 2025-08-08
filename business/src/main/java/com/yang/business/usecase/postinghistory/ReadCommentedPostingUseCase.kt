package com.yang.business.usecase.postinghistory

import com.yang.business.common.DataResourceResult
import com.yang.business.model.Posting
import com.yang.business.repository.PostingHistoryRepository
import kotlinx.coroutines.flow.Flow

class ReadCommentedPostingUseCase(val postingHistoryRepository: PostingHistoryRepository) {
    suspend operator fun invoke(userId: String) : Flow<DataResourceResult<List<Posting>>> {
        return postingHistoryRepository.readCommentedPosting(userId)
    }
}