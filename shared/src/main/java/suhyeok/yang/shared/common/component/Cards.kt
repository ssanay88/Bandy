package suhyeok.yang.shared.common.component

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import suhyeok.yang.shared.R
import suhyeok.yang.shared.ui.theme.White

@Composable
fun WhiteRoundedCornerCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = White,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.white_rounded_corner_card_elevation))
    ) {
        content()
    }

}