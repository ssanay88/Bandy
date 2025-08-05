package suhyeok.yang.shared.common.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import suhyeok.yang.shared.R
import suhyeok.yang.shared.ui.theme.Primary

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(dimensionResource(R.dimen.loading_icon_size)),
            color = Primary,
            strokeWidth = dimensionResource(R.dimen.loading_icon_stroke_width)
        )
    }
}