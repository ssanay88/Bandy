package suhyeok.yang.feature.ui.profile

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yang.business.enums.Instrument
import suhyeok.yang.feature.R
import suhyeok.yang.shared.R as SharedR
import suhyeok.yang.shared.common.component.CircleImageView
import suhyeok.yang.shared.common.component.SectionDivider
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.ui.theme.SuitFontFamily

@Composable
fun ProfileScreen(
    onUpdateProfileClick: () -> Unit,
    onPostingsHistoryClick: () -> Unit,
    onManageBandClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.isBandLeader()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MyProfileInfoSection(
            uiState.profileImageUrl,
            uiState.userNickname,
            uiState.userInstrument
        )
        DescriptionSection(uiState.userDescription)
        SectionDivider()
        ProfileMenuSection(
            isBandLeader = uiState.isBandLeader,
            onUpdateProfileClick = onUpdateProfileClick,
            onPostingsHistoryClick = onPostingsHistoryClick,
            onManageBandClick = onManageBandClick,
            onNotificationClick = onNotificationClick
        )
        SectionDivider()
    }
}

@Composable
fun MyProfileInfoSection(
    profileImageUrl: String,
    userNickname: String,
    userInstrument: Instrument
) {
    Row(
        modifier = Modifier
            .height(dimensionResource(R.dimen.profile_info_section_height))
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_10dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleImageView(
            imageResId = profileImageUrl,
            imageDescription = "user profile image",
            imageSize = dimensionResource(R.dimen.profile_info_image_size),
            placeHolderResId = R.drawable.bandy_logo_tertiary_color,
            errorResId = R.drawable.bandy_logo_tertiary_color
        )

        Column(
            modifier = Modifier.padding(start = dimensionResource(R.dimen.profile_info_name_start_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_10dp))
        ) {
            NicknameText(userNickname)
            InstrumentText(userInstrument)
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
fun DescriptionSection(userDescription: String) {
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
            text = userDescription,
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
    isBandLeader: Boolean,
    onUpdateProfileClick: () -> Unit,
    onPostingsHistoryClick: () -> Unit,
    onManageBandClick: () -> Unit,
    onNotificationClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_10dp))
    ) {
        UpdateProfile(onUpdateProfileClick)
        PostingHistory(onPostingsHistoryClick)
        if (isBandLeader) ManageBand(onManageBandClick)
        ManageBand(onManageBandClick)
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
            .throttleClick { onClick() },
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
