package suhyeok.yang.data.remote.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yang.business.common.DataResourceResult
import com.yang.business.model.User
import kotlinx.coroutines.tasks.await
import suhyeok.yang.data.datasource.UserDataSource
import suhyeok.yang.data.mapper.toBusinessUser
import suhyeok.yang.data.mapper.toFirestoreUserDTO
import suhyeok.yang.data.remote.dto.UserDTO

class FirestoreUserDataSourceImpl: UserDataSource {

    companion object {
        const val USER_COLLECTION = "users"
    }

    val db = Firebase.firestore

    override suspend fun readUser(userId: String): DataResourceResult<User> = runCatching {
        val userSnapshot = db.collection(USER_COLLECTION)
            .whereEqualTo("_userId", userId)
            .get()
            .await()

        val userDTO = userSnapshot.toObjects(UserDTO::class.java).firstOrNull()

        if (userDTO != null) {
            DataResourceResult.Success(userDTO.toBusinessUser())
        } else {
            DataResourceResult.Failure(Exception("User not found"))
        }
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun createUser(newUser: User): DataResourceResult<Unit> = runCatching {
        db.collection(USER_COLLECTION)
            .add(newUser.toFirestoreUserDTO())
            .await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun updateUser(updatedUser: User): DataResourceResult<Unit> = runCatching {
        db.collection(USER_COLLECTION)
            .whereEqualTo("_userId", updatedUser.userId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.size() > 0) {
                    task.result.forEach {
                        db.collection(USER_COLLECTION).document(it.id)
                            .update(updatedUser.toFirestoreUserDTO())
                    }
                }
            }.await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun deleteUser(userId: String): DataResourceResult<Unit> = runCatching {
        db.collection(USER_COLLECTION)
            .whereEqualTo("_userId", userId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result.size() > 0) {
                    task.result.forEach {
                        db.collection(USER_COLLECTION).document(it.id)
                            .delete()
                    }
                }
            }.await()
        DataResourceResult.Success(Unit)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }

    override suspend fun searchUserByNickname(nickname: String): DataResourceResult<List<User>> = runCatching {
        val usersSnapshot = db.collection(USER_COLLECTION)
            .whereGreaterThanOrEqualTo("_nickName", nickname)
            .whereLessThanOrEqualTo("_nickName", nickname + "\uf8ff")
            .get()
            .await()

        val users = usersSnapshot.documents.mapNotNull { it.toObject(UserDTO::class.java)?.toBusinessUser() }

        DataResourceResult.Success(users)
    }.getOrElse {
        DataResourceResult.Failure(it)
    }
}