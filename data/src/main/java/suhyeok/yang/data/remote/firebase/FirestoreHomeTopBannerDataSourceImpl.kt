package suhyeok.yang.data.remote.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yang.business.common.DataResourceResult
import com.yang.business.model.HomeTopBanner
import kotlinx.coroutines.tasks.await
import suhyeok.yang.data.datasource.HomeTopBannerDataSource
import suhyeok.yang.data.mapper.toBusinessHomeTopBannerList
import suhyeok.yang.data.remote.dto.HomeTopBannerDTO
import javax.inject.Inject

class FirestoreHomeTopBannerDataSourceImpl @Inject constructor(): HomeTopBannerDataSource {

    companion object {
        const val HOME_TOP_BANNER_COLLECTION = "homeTopBanners"
    }

    val db = Firebase.firestore

    override suspend fun readHomeTopBanner(): DataResourceResult<List<HomeTopBanner>> = runCatching {
        val homeTopBannerSnapshot = db.collection(HOME_TOP_BANNER_COLLECTION)
            .get()
            .await()

        val homeTopBannerDTOList = homeTopBannerSnapshot.toObjects(HomeTopBannerDTO::class.java)
        DataResourceResult.Success(homeTopBannerDTOList.toBusinessHomeTopBannerList())
    }.getOrElse {
        DataResourceResult.Failure(it)
    }
}