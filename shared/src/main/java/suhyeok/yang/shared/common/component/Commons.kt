package suhyeok.yang.shared.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import suhyeok.yang.shared.R
import suhyeok.yang.shared.ui.theme.LightGray
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.Tertiary

@Composable
fun ThinDivider() {
    Spacer(modifier = Modifier.fillMaxWidth().height(dimensionResource(id = R.dimen.thin_divider_height)).background(Tertiary))
}

@Composable
fun SectionDivider() {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.section_divider_height))
            .background(color = LightGray)
    )
}

@Composable
fun LoadingCircularProgressIndicator() {
    CircularProgressIndicator(
        modifier = Modifier.size(dimensionResource(R.dimen.loading_icon_size)),
        color = Primary,
        strokeWidth = dimensionResource(R.dimen.loading_icon_stroke_width)
    )
}