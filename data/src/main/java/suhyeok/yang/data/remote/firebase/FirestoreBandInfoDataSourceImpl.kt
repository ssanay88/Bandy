package suhyeok.yang.data.remote.firebase

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yang.business.common.DataResourceResult
import com.yang.business.model.User
import kotlinx.coroutines.tasks.await
import suhyeok.yang.data.datasource.BandInfoDataSource
import suhyeok.yang.data.mapper.toFirestoreUserDTO
import javax.inject.Inject

class FirestoreBandInfoDataSourceImpl @Inject constructor() : BandInfoDataSource {

    companion object {
        const val USER_COLLECTION = "users"
        const val BAND_COLLECTION = "bands"
    }

    override suspend fun addMember(
        bandId: String,
        userId: String,
    ): DataResourceResult<Unit> = runCatching {
        val userDocument = Firebase.firestore
            .collection(USER_COLLECTION)
            .document(userId)
            .get()
            .await()

        if (!userDocument.exists()) {
            return DataResourceResult.Failure(Exception("User with ID $userId not found"))
        }

        userDocument.reference.update("_bandId", bandId).await()

        val userData = userDocument.toObject(User::class.java)
            ?: throw IllegalStateException("User data is null")

        val bandQuerySnapshot = Firebase.firestore
            .collection(BAND_COLLECTION)
            .whereEqualTo("_bandId", bandId)
            .get()
            .await()

        if (bandQuerySnapshot.isEmpty) {
            return DataResourceResult.Failure(Exception("Band with ID $bandId not found"))
        }

        val bandDocumentReference = bandQuerySnapshot.documents.first().reference

        val currentMembers = bandDocumentReference.get().await()
            .get("_members") as? ArrayList<Map<String, Any>> ?: arrayListOf()

        val isMemberExist = currentMembers.any { it["id"] == userData.userId }
        if (isMemberExist) {
            return DataResourceResult.Success(Unit)
        }

        currentMembers.add(userData.toFirestoreUserDTO())

        // 밴드 문서 업데이트
        bandDocumentReference.update("_members", currentMembers).await()

        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun removeMember(
        bandId: String,
        userId: String,
    ): DataResourceResult<Unit> = runCatching {
        // 1. 밴드 컬렉션에서 bandId에 해당하는 문서 가져오기
        val bandQuerySnapshot = Firebase.firestore
            .collection(BAND_COLLECTION)
            .whereEqualTo("_bandId", bandId)
            .get()
            .await()

        if (bandQuerySnapshot.isEmpty) {
            // 밴드가 존재하지 않는 경우
            return DataResourceResult.Failure(Exception("Band with ID $bandId not found"))
        }

        // 밴드 문서 참조
        val bandDocumentReference = bandQuerySnapshot.documents.first().reference

        val currentMembers = bandDocumentReference.get().await()
            .get("_members") as? ArrayList<Map<String, Any>> ?: arrayListOf()

        val updatedMembers = currentMembers.filter { it["_userId"] != userId }

        if (updatedMembers.size == currentMembers.size) {
            return DataResourceResult.Success(Unit) // 멤버가 없으므로 성공으로 처리
        }

        bandDocumentReference.update("_members", updatedMembers).await()

        val userDocument = Firebase.firestore
            .collection(USER_COLLECTION)
            .document(userId)
            .get()
            .await()

        if (!userDocument.exists()) {
            return DataResourceResult.Failure(Exception("User with ID $userId not found"))
        }

        // 유저의 밴드 정보 업데이트
        userDocument.reference.update("_bandId", "").await()

        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }


    override suspend fun changeLeader(
        bandId: String,
        prevLeaderId: String,
        newLeaderId: String
    ): DataResourceResult<Unit> = runCatching {
        updateLeaderInBandDB(bandId, prevLeaderId, newLeaderId)
        updateLeaderInUserDB(prevLeaderId, newLeaderId)
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    private suspend fun updateLeaderInBandDB(bandId: String, prevLeaderId: String, newLeaderId: String) {
        val bandCollection = Firebase.firestore.collection(BAND_COLLECTION)

        // Band 데이터의 leaderId 값 수정
        val bandQuerySnapshot = bandCollection
            .whereEqualTo("_bandId", bandId)
            .get()
            .await()

        if (bandQuerySnapshot.isEmpty) {
            throw Exception("Band with ID $bandId not found.")
        }

        val bandDocumentReference = bandQuerySnapshot.documents.first().reference

        bandDocumentReference.apply {
            update("_leaderId", newLeaderId).await()
        }

        // Band 데이터의 members 데이터 값 수정
        val currentMembers = bandDocumentReference.get().await().get("_members") as? ArrayList<Map<String, Any>> ?: arrayListOf()

        val updatedMembers = currentMembers.map { member ->
            val mutableMember = member.toMutableMap()

            val memberUserId = mutableMember["_userId"] as? String

            if (memberUserId == prevLeaderId) {
                mutableMember["_isLeader"] = false
            }
            if (memberUserId == newLeaderId) {
                mutableMember["_isLeader"] = true
            }

            mutableMember.toMap()
        }

        bandDocumentReference.update("_members", updatedMembers).await()
    }

    private suspend fun updateLeaderInUserDB(prevLeaderId: String, newLeaderId: String) {
        // Users 데이터의 isLeader Boolean 값 수정
        val prevLeaderQuerySnapshot = Firebase.firestore.collection(USER_COLLECTION)
            .whereEqualTo("_userId", prevLeaderId)
            .get()
            .await()

        val newLeaderQuerySnapshot = Firebase.firestore.collection(USER_COLLECTION)
            .whereEqualTo("_userId", newLeaderId)
            .get()
            .await()

        if (!prevLeaderQuerySnapshot.isEmpty && !newLeaderQuerySnapshot.isEmpty) {
            prevLeaderQuerySnapshot.documents.forEach { document ->
                document.reference.update("_isLeader", false).await()
            }
            newLeaderQuerySnapshot.documents.forEach { document ->
                document.reference.update("_isLeader", true).await()
            }
        }
    }
}