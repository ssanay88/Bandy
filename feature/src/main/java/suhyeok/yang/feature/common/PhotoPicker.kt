package suhyeok.yang.feature.common

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import javax.inject.Inject

class PhotoPicker @Inject constructor(
    private val launcher: ManagedActivityResultLauncher<Array<String>, Uri?>
) {
    fun pickImage() {
        launcher.launch(arrayOf("image/*"))
    }
}