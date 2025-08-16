package suhyeok.yang.bandy

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.persistentCacheSettings
import com.google.firebase.ktx.Firebase
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.HiltAndroidApp
import suhyeok.yang.bandy.di.ApplicationContainerImpl
import suhyeok.yang.shared.di.ApplicationContainer
import suhyeok.yang.shared.di.ApplicationContainerProvider

const val COIL_MEMORY_CACHE_SIZE_PERCENT = 0.15
const val COIL_DISK_CACHE_DIR_NAME = "coil_disk_cache"
const val COIL_DISK_CACHE_MAX_SIZE = 1024 * 1024 * 50

@HiltAndroidApp
class BandyApplication: Application(), ApplicationContainerProvider, SingletonImageLoader.Factory {
    private lateinit var appContainer: ApplicationContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = ApplicationContainerImpl(context = this)
        setUpFirestoreLocalSinkCache()
        initNaverLogin()
    }

    override fun provideApplicationContainer(): ApplicationContainer {
        return appContainer
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader = ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, COIL_MEMORY_CACHE_SIZE_PERCENT)
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(filesDir.resolve(COIL_DISK_CACHE_DIR_NAME))
                .maximumMaxSizeBytes(COIL_DISK_CACHE_MAX_SIZE.toLong())
                .build()
        }.build()

    fun setUpFirestoreLocalSinkCache(){
        val settings = firestoreSettings {
            setLocalCacheSettings(persistentCacheSettings {
                /**
                 * Cache Size Setting(100MB)
                 */
                setSizeBytes(100 * 1024 * 1024)
            })
        }
        Firebase.firestore.firestoreSettings = settings
    }

    fun initNaverLogin() {
        NaverIdLoginSDK.initialize(
            this,
            BuildConfig.NAVER_API_CLIENT_ID,
            BuildConfig.NAVER_API_CLIENT_SECRET,
            BuildConfig.NAVER_API_CLIENT_NAME
        )
    }
}