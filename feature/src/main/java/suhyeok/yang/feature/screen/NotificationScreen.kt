package suhyeok.yang.feature.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.yang.business.model.Notification
import suhyeok.yang.feature.MockData.mockNotificationDataList
import suhyeok.yang.feature.R
import suhyeok.yang.shared.common.component.RightIconText
import suhyeok.yang.shared.common.component.ThinDivider
import suhyeok.yang.shared.common.component.WhiteRoundedCornerCard
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.ui.theme.SuitFontFamily

// TODO 알림을 보여주는 화면
@Composable
fun NotificationScreen() {
    LazyColumn(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_5dp)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp)),
    ) {
        items(mockNotificationDataList) { notification ->
            NotificationItemView(notification)
        }
    }
}

@Composable
fun NotificationItemView(notification: Notification) {
    WhiteRoundedCornerCard(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_10dp)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
        ) {
            Text(
                text = notification.title,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            ThinDivider()
            Text(
                text = notification.content,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.bodyLarge
            )
            RightIconText(
                rowModifier = Modifier.align(Alignment.End).throttleClick {

                },
                text = "바로 가기",
                textStyle = MaterialTheme.typography.labelSmall,
                textModifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "바로 가기",
                    modifier = Modifier.size(dimensionResource(R.dimen.notification_item_view_arrow_icon_size))
                )
            }

        }
    }
}