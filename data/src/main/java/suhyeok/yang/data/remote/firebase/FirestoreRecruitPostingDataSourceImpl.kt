package suhyeok.yang.data.remote.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yang.business.common.DataResourceResult
import com.yang.business.model.RecruitPosting
import kotlinx.coroutines.tasks.await
import suhyeok.yang.data.datasource.RecruitPostingDataSource
import suhyeok.yang.data.mapper.toBusinessRecruitPosting
import suhyeok.yang.data.mapper.toBusinessRecruitPostingList
import suhyeok.yang.data.mapper.toFirestoreRecruitPostingDTO
import suhyeok.yang.data.remote.dto.RecruitPostingDTO
import javax.inject.Inject

class FirestoreRecruitPostingDataSourceImpl @Inject constructor(): RecruitPostingDataSource {

    companion object {
        const val RECRUIT_POSTING_COLLECTION = "recruitPostings"
    }

    val db = Firebase.firestore

    override suspend fun createRecruitPosting(newRecruitPosting: RecruitPosting): DataResourceResult<Unit> = runCatching {
        db.collection(RECRUIT_POSTING_COLLECTION)
            .add(newRecruitPosting.toFirestoreRecruitPostingDTO())
            .await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun readRecruitPosting(postingId: String): DataResourceResult<RecruitPosting> = runCatching {
        val recruitPostingSnapshot = db.collection(RECRUIT_POSTING_COLLECTION)
            .whereEqualTo("_recruitPostingId", postingId)
            .get()
            .await()

        val recruitPostingDTO = recruitPostingSnapshot.toObjects(RecruitPostingDTO::class.java).firstOrNull()

        if (recruitPostingDTO != null) {
            DataResourceResult.Success(recruitPostingDTO.toBusinessRecruitPosting())
        } else {
            DataResourceResult.Failure(Exception("Recruit Posting not found"))
        }
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun readRecruitPostingList(): DataResourceResult<List<RecruitPosting>> = runCatching {
        val recruitPostingListSnapshot = db.collection(RECRUIT_POSTING_COLLECTION)
            .get()
            .await()

        val recruitPostingDTOList = recruitPostingListSnapshot.toObjects(RecruitPostingDTO::class.java)
        DataResourceResult.Success(recruitPostingDTOList.toBusinessRecruitPostingList().sortedByDescending { it.createdAt })
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun updateRecruitPosting(updatedRecruitPosting: RecruitPosting): DataResourceResult<Unit> = runCatching {
        db.collection(RECRUIT_POSTING_COLLECTION)
            .whereEqualTo("_recruitPostingId", updatedRecruitPosting.recruitPostingId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.size() > 0) {
                    task.result.forEach {
                        db.collection(RECRUIT_POSTING_COLLECTION).document(it.id)
                            .update(updatedRecruitPosting.toFirestoreRecruitPostingDTO())
                    }
                }
            }.await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun deleteRecruitPosting(postingId: String): DataResourceResult<Unit> = runCatching {
        db.collection(RECRUIT_POSTING_COLLECTION)
            .whereEqualTo("_recruitPostingId", postingId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.size() > 0) {
                    task.result.forEach {
                        db.collection(RECRUIT_POSTING_COLLECTION).document(it.id)
                            .delete()
                    }
                }
            }.await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }
}