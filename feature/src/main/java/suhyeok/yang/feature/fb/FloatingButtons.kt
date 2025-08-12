package suhyeok.yang.feature.fb

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import suhyeok.yang.feature.R
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.White

@Composable
fun RecruitFloatingButton(
    onCreateBandClick: () -> Unit,
    onRecruitMemberClick: () -> Unit
) {
    var fabExpanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End
    ) {
        if (fabExpanded) {
            FabItemView(
                title = stringResource(R.string.create_band_text),
                iconResId = R.drawable.ic_create_band,
                onClick = onCreateBandClick
            )

            FabItemView(
                title = stringResource(R.string.recruit_member_text),
                iconResId = R.drawable.ic_recruit_member,
                onClick = onRecruitMemberClick
            )
        }

        FloatingActionButton(
            containerColor = Primary,
            contentColor = White,
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(dimensionResource(R.dimen.elevation_8dp)),
            onClick = { fabExpanded = !fabExpanded }
        ) {
            if (fabExpanded) {
                Icon(
                    modifier = Modifier.size(dimensionResource(R.dimen.floating_button_icon_size)),
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = White
                )
            } else {
                Icon(
                    modifier = Modifier.size(dimensionResource(R.dimen.floating_button_icon_size)),
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = White
                )
            }
        }
    }
}

@Composable
fun HomeFloatingButton(
    onCreatePostingClick: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier.height(dimensionResource(R.dimen.home_floating_button_height)),
        containerColor = Primary,
        contentColor = White,
        shape = RoundedCornerShape(dimensionResource(R.dimen.corner_round_8dp)),
        elevation = FloatingActionButtonDefaults.elevation(dimensionResource(R.dimen.elevation_8dp)),
        onClick = {}
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(R.dimen.floating_button_item_view_padding)).throttleClick { onCreatePostingClick() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(dimensionResource(R.dimen.floating_button_item_view_icon_size)),
                painter = painterResource(id = R.drawable.ic_posting),
                contentDescription = null,
                tint = White
            )
            Text(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.floating_button_item_view_icon_text_space)),
                text = stringResource(R.string.create_posting_text),
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }
    }
}

@Composable
fun FabItemView(
    title: String,
    titleStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    iconResId: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(dimensionResource(R.dimen.floating_button_item_view_width))
            .padding(dimensionResource(R.dimen.padding_5dp))
            .throttleClick { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.elevation_8dp)),
        colors = CardDefaults.cardColors(
            containerColor = Primary
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.floating_button_item_view_padding)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(dimensionResource(R.dimen.floating_button_item_view_icon_size)),
                painter = painterResource(id = iconResId),
                contentDescription = null,
                tint = White
            )
            Text(
                modifier = Modifier.padding(start = dimensionResource(R.dimen.floating_button_item_view_icon_text_space)),
                text = title,
                fontFamily = SuitFontFamily,
                style = titleStyle,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }
    }
}