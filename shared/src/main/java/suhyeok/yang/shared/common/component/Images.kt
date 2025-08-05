package suhyeok.yang.shared.common.component

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage

@Composable
fun CircleImageView(
    modifier: Modifier = Modifier,
    imageResId: Any,
    imageSize: Dp,
    imageDescription: String = "",
    placeHolderResId: Int = 0,
    errorResId: Int = 0,
) {
    AsyncImage(
        model = imageResId,
        contentDescription = imageDescription,
        placeholder = if (placeHolderResId == 0) null else painterResource(placeHolderResId),
        error = if (errorResId == 0) null else painterResource(errorResId),
        modifier = modifier
            .size(imageSize)
            .aspectRatio(1f)
            .clip(CircleShape),
        contentScale = ContentScale.FillBounds
    )
}