package suhyeok.yang.data.remote.firebase

import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yang.business.common.DataResourceResult
import com.yang.business.model.Band
import kotlinx.coroutines.tasks.await
import suhyeok.yang.data.datasource.BandDataSource
import suhyeok.yang.data.mapper.toBusinessBand
import suhyeok.yang.data.mapper.toBusinessBandList
import suhyeok.yang.data.mapper.toFirestoreBandDTO
import suhyeok.yang.data.remote.dto.BandDTO
import kotlin.jvm.java

class FirestoreBandDataSourceImpl: BandDataSource {

    companion object {
        const val BAND_COLLECTION = "bands"
        const val POPULAR_BANDS_COUNT = 10
    }
    val db = Firebase.firestore

    override suspend fun createBand(newBand: Band): DataResourceResult<Unit> = runCatching {
        db.collection(BAND_COLLECTION)
            .add(newBand.toFirestoreBandDTO())
            .await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun readBand(bandId: String): DataResourceResult<Band> = runCatching {
        val bandSnapshot = db.collection(BAND_COLLECTION)
            .whereEqualTo("_bandId", bandId)
            .limit(1)
            .get()
            .await()

        val bandDTO = bandSnapshot.documents.first().toObject(BandDTO::class.java)
        if (bandDTO != null) {
            DataResourceResult.Success(bandDTO.toBusinessBand())
        } else {
            DataResourceResult.Failure(Exception("Band not found"))
        }
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun readPopularBand(): DataResourceResult<List<Band>> = runCatching {
        val bandListSnapshot = db.collection(BAND_COLLECTION)
            .orderBy("_viewCount", Query.Direction.DESCENDING)
            .get()
            .await()

        val popularBandList = bandListSnapshot.toObjects(BandDTO::class.java).take(POPULAR_BANDS_COUNT)
        DataResourceResult.Success(popularBandList.toBusinessBandList())
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun readRecruitingBand(): DataResourceResult<List<Band>> = runCatching {
        val bandListSnapshot = db.collection(BAND_COLLECTION)
            .whereEqualTo("_isRecruiting", true)
            .get()
            .await()

        val recruitingBandList = bandListSnapshot.toObjects(BandDTO::class.java)
        DataResourceResult.Success(recruitingBandList.toBusinessBandList())
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun readBandList(): DataResourceResult<List<Band>> = runCatching {
        val bandListSnapshot = db.collection(BAND_COLLECTION)
            .get()
            .await()

        val bandDTOList = bandListSnapshot.toObjects(BandDTO::class.java)
        DataResourceResult.Success(bandDTOList.toBusinessBandList())
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun updateBand(updatedBand: Band): DataResourceResult<Unit> = runCatching {
        db.collection(BAND_COLLECTION)
            .whereEqualTo("_bandId", updatedBand.bandId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.size() > 0) {
                    task.result.forEach {
                        db.collection(BAND_COLLECTION).document(it.id)
                            .update(updatedBand.toFirestoreBandDTO())
                    }
                }
            }.await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun deleteBand(bandId: String): DataResourceResult<Unit> = runCatching {
        db.collection(BAND_COLLECTION)
            .whereEqualTo("_bandId", bandId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.size() > 0) {
                    task.result.forEach {
                        db.collection(BAND_COLLECTION).document(it.id)
                            .delete()
                    }
                }
            }.await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }
}