package suhyeok.yang.feature.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.yang.business.model.User
import suhyeok.yang.feature.MockData.myBandMemberDataList
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.CancelButton
import suhyeok.yang.feature.common.components.PrimaryColorRoundedButton
import suhyeok.yang.shared.common.component.CircleImageView
import suhyeok.yang.shared.common.component.OutlinedSpinner
import suhyeok.yang.shared.common.component.RightIconText
import suhyeok.yang.shared.ui.theme.LightGray
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import kotlin.math.exp

/**
 * 밴드 관리 스크린
 */
@Composable
fun ManageBandScreen(onCancelClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_10dp))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_24dp))
    ) {
        BandProfileUpdateSection()
        BandNameUpdateSection()
        BandRegionUpdateSection()
        BandMemberUpdateSection()
        BandIntroduceUpdateSection()
        ManageBandScreenButtonSection(onCancelClick)
    }
}

@Composable
fun BandProfileUpdateSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.bandy_logo_tertiary_color),
                contentDescription = stringResource(R.string.default_profile_image_descript),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(120.dp).align(Alignment.Center)
            )
            Image(
                painter = painterResource(R.drawable.ic_change_profile_img),
                contentDescription = stringResource(R.string.change_profile_image_icon_descript),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(40.dp).align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
fun BandNameUpdateSection() {
    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(stringResource(R.string.band_name_regist_text_field_title)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BandRegionUpdateSection() {
    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
        ) {
            OutlinedSpinner(
                modifier = Modifier.weight(0.5f),
                items = listOf("서울특별시","경기도","경상북도","경상남도"),
                selectedItemIdx = 0,
                label = stringResource(R.string.region),
                onValueChange = {}
            )

            OutlinedSpinner(
                modifier = Modifier.weight(0.5f),
                items = listOf("홍대입구","신림","용산","잠실"),
                selectedItemIdx = 0,
                label = stringResource(R.string.region),
                onValueChange = {}
            )
        }
    }
}

@Composable
fun BandMemberUpdateSection() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        myBandMemberDataList.forEach { member ->
            ManageBandMemberItemView(member)
        }
    }
}

@Composable
fun ManageBandMemberItemView(member: User) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_10dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImageView(
            modifier = Modifier,
            imageResId = member.profileImageUrl,
            imageSize = dimensionResource(R.dimen.added_member_profile_image_size),
            imageDescription = member.nickName,
            placeHolderResId = R.drawable.bandy_logo_tertiary_color,
            errorResId = R.drawable.bandy_logo_tertiary_color
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = dimensionResource(R.dimen.padding_10dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RightIconText(
                rowModifier = Modifier.weight(0.8f),
                text = "${member.nickName} (${member.userName})",
                textStyle = MaterialTheme.typography.titleLarge,
                textFontWeight = FontWeight.Bold,
                textModifier = Modifier
            ) {
                if (member.isLeader) {
                    Icon(
                        painter = painterResource(R.drawable.ic_band_leader),
                        contentDescription = "band leader icon",
                        modifier = Modifier.size(dimensionResource(R.dimen.manage_band_screen_leader_icon_size)),
                        tint = Color.Unspecified
                    )
                }
            }

            Text(
                text = member.instrument.toString(),
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(0.4f),
                textAlign = TextAlign.End
            )

            if (!member.isLeader) {
                Box() {
                    IconButton(
                        onClick = {
                            expanded = !expanded
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_setting),
                            contentDescription = stringResource(R.string.manage_member_icon_description),
                            modifier = Modifier
                                .padding(horizontal = dimensionResource(R.dimen.padding_5dp))
                                .size(dimensionResource(R.dimen.manage_member_icon_size)),
                            tint = Color.Unspecified
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier.background(LightGray),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        ManageMemberDropdownMenu(stringResource(R.string.delegate_leader_text)) {}
                        ManageMemberDropdownMenu(stringResource(R.string.member_detail_info_text)) {}
                        ManageMemberDropdownMenu(stringResource(R.string.remove_member_text)) {}
                    }
                }
            }
        }
    }
}

@Composable
fun ManageMemberDropdownMenu(
    text: String,
    onClick: () -> Unit
) {
    DropdownMenuItem(
        text = { Text(text) },
        modifier = Modifier.background(LightGray),
        onClick = { onClick() }
    )
}

@Composable
fun BandIntroduceUpdateSection() {
    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().heightIn(min = dimensionResource(R.dimen.band_introduce_text_field_min_height)),
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.band_introduce_regist_text_field_title)) }
        )
    }
}

@Composable
fun ManageBandScreenButtonSection(onCancelClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
    ) {
        CancelButton(Modifier.weight(0.5f), onCancelClick)
        UpdateBandButton(Modifier.weight(0.5f))
    }
}

@Composable
fun UpdateBandButton(modifier: Modifier = Modifier) {
    PrimaryColorRoundedButton(
        buttonModifier = modifier,
        butttonText = stringResource(R.string.create_band_button_text),
        onClick = {}
    )
}