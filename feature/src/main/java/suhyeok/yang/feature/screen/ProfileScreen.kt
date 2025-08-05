package suhyeok.yang.feature.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.yang.business.enums.Instrument
import suhyeok.yang.feature.R
import suhyeok.yang.shared.R as SharedR
import suhyeok.yang.shared.common.component.CircleImageView
import suhyeok.yang.shared.common.component.SectionDivider
import suhyeok.yang.shared.ui.theme.SuitFontFamily

@Composable
fun ProfileScreen(
    onUpdateProfileClick: () -> Unit,
    onPostingsHistoryClick: () -> Unit,
    onManageBandClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MyProfileInfoSction()
        // MyBandInfo() 내 밴드가 있는 경우
        DescriptionSection()
        SectionDivider()
        ProfileMenuSection(
            onUpdateProfileClick = onUpdateProfileClick,
            onPostingsHistoryClick = onPostingsHistoryClick,
            onManageBandClick = onManageBandClick,
            onNotificationClick = onNotificationClick
        )
        SectionDivider()
    }
}

@Composable
fun MyProfileInfoSction() {
    Row(
        modifier = Modifier
            .height(dimensionResource(R.dimen.profile_info_section_height))
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_10dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleImageView(
            imageResId = "data:image/jpeg;base64,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",
            imageDescription = "default profile image",
            imageSize = dimensionResource(R.dimen.profile_info_image_size)
        )

        Column(
            modifier = Modifier.padding(start = dimensionResource(R.dimen.profile_info_name_start_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_10dp))
        ) {
            NicknameText("SSANAY")
            InstrumentText(Instrument.GUITAR)
        }
    }
}

@Composable
fun NicknameText(nickName: String) {
    Text(
        text = nickName,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun InstrumentText(instrument: Instrument) {
    Text(
        text = instrument.toString(),
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun DescriptionSection() {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_10dp))
    ) {
        Text(
            text = stringResource(R.string.introduction_text),
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "안녕하세요 저는 기타 2년차 SSANAY입니다. 주로 신림, 사당, 홍대 위주에서 합주를 하고 있으며 직장인 밴드에서 메인 기타를 맡고있습니다. ",
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = dimensionResource(R.dimen.profile_description_min_height))
                .padding(top = dimensionResource(R.dimen.profile_description_top_padding))

        )

    }
}

@Composable
fun ProfileMenuSection(
    onUpdateProfileClick: () -> Unit,
    onPostingsHistoryClick: () -> Unit,
    onManageBandClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    val isBandLeader = true // TODO DB에 저장한 유저의 정보로 판단하도록 수정 필요

    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_10dp))
    ) {
        UpdateProfile(onUpdateProfileClick)
        PostingHistory(onPostingsHistoryClick)
        if (isBandLeader) ManageBand(onManageBandClick)
        NotificationPopup(onNotificationClick)
    }
}

@Composable
fun ProfileMenuRow(
    icon: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.profile_menu_height))
            .padding(vertical = dimensionResource(R.dimen.padding_vertical_5dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = title,
            modifier = Modifier
                .size(dimensionResource(R.dimen.profile_menu_icon_size))
                .padding(dimensionResource(R.dimen.padding_5dp))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.band_image_corner)))
        )
        Text(
            text = title,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.profile_menu_text_start_padding))
        )
    }
}

@Composable
fun UpdateProfile(onUpdateProfileClick: () -> Unit) {
    ProfileMenuRow(icon = R.drawable.ic_edit, title = stringResource(R.string.update_profile_menu_text)) {
        onUpdateProfileClick()
    }
}

@Composable
fun PostingHistory(onPostingsHistoryClick: () -> Unit) {
    ProfileMenuRow(icon = R.drawable.ic_history, title = stringResource(R.string.posting_history_menu_text)) {
        onPostingsHistoryClick()
    }
}

@Composable
fun ManageBand(onManageBandClick: () -> Unit) {
    ProfileMenuRow(icon = R.drawable.ic_manage_band, title = stringResource(R.string.manage_band_menu_text)) {
        onManageBandClick()
    }
}

@Composable
fun NotificationPopup(onNotificationClick: () -> Unit) {
    ProfileMenuRow(icon = SharedR.drawable.ic_outline_notification, title = stringResource(R.string.notification_menu_text)) {
        onNotificationClick()
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        onUpdateProfileClick = {},
        onPostingsHistoryClick = {},
        onManageBandClick = {},
        onNotificationClick = {}
    )
}


@Preview(showBackground = true)
@Composable
fun MyProfileInfoSctionPreview() {
    MyProfileInfoSction()
}