package suhyeok.yang.feature.common.components

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import suhyeok.yang.feature.common.PhotoPicker

@Composable
fun rememberPhotoPicker(
    onImagePicked: (String) -> Unit
) : PhotoPicker {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()) { uri ->
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                Log.d("tngur","pick : ${uri.toString()}")
                onImagePicked(uri.toString())
            }
        }

    return remember { PhotoPicker(launcher) }
}