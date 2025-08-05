package suhyeok.yang.data.remote.firebase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yang.business.common.DataResourceResult
import com.yang.business.model.Posting
import kotlinx.coroutines.tasks.await
import suhyeok.yang.data.datasource.PostingDataSource
import suhyeok.yang.data.mapper.toBusinessPosting
import suhyeok.yang.data.mapper.toBusinessPostingList
import suhyeok.yang.data.mapper.toFirestorePostingDTO
import suhyeok.yang.data.remote.dto.PostingDTO

class FirestorePostingDataSourceImpl: PostingDataSource {

    companion object {
        const val POSTING_COLLECTION = "postings"
    }

    val db = Firebase.firestore

    override suspend fun createPosting(newPosting: Posting): DataResourceResult<Unit> = runCatching {
        db.collection(POSTING_COLLECTION)
            .add(newPosting.toFirestorePostingDTO())
            .await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun readPosting(): DataResourceResult<List<Posting>> = runCatching {
        val postingSnapshot = db.collection(POSTING_COLLECTION)
            .get()
            .await()

        val postingDTOList = postingSnapshot.toObjects(PostingDTO::class.java)
        DataResourceResult.Success(postingDTOList.toBusinessPostingList())
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun readPostingDetail(postingId: String): DataResourceResult<Posting> = runCatching {
        val postingDetailSnapshot = db.collection(POSTING_COLLECTION)
            .whereEqualTo("_postingId", postingId)
            .get()
            .await()

        val postingDTOList = postingDetailSnapshot.toObjects(PostingDTO::class.java).firstOrNull()

        if (postingDTOList != null) {
            DataResourceResult.Success(postingDTOList.toBusinessPosting())
        } else {
            DataResourceResult.Failure(Exception("Posting not found"))
        }
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun updatePosting(updatedPosting: Posting): DataResourceResult<Unit> = runCatching {
        db.collection(POSTING_COLLECTION)
            .whereEqualTo("_postingId", updatedPosting.postingId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.size() > 0) {
                    task.result.forEach {
                        db.collection(POSTING_COLLECTION).document(it.id)
                            .update(updatedPosting.toFirestorePostingDTO())
                    }
                }
            }.await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun deletePosting(postingId: String): DataResourceResult<Unit> = runCatching {
        db.collection(POSTING_COLLECTION)
            .whereEqualTo("_postingId", postingId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.size() > 0) {
                    task.result.forEach {
                        db.collection(POSTING_COLLECTION).document(it.id)
                            .delete()
                    }
                }
            }.await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }
}