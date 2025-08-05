package suhyeok.yang.feature.screen

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.yang.business.model.User
import suhyeok.yang.feature.MockData
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.CancelButton
import suhyeok.yang.feature.common.components.PrimaryColorRoundedButton
import suhyeok.yang.feature.ui.profile.TitleText
import suhyeok.yang.shared.common.component.CircleImageView
import suhyeok.yang.shared.common.component.LeftIconText
import suhyeok.yang.shared.common.component.OutlinedSpinner
import suhyeok.yang.shared.common.component.RightIconText
import suhyeok.yang.shared.ui.theme.Gray
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.White

@Preview(showBackground = true)
@Composable
fun CreateBandScreen(navController: NavController = rememberNavController()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_10dp))
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_24dp))
    ) {
        BandImageRegistSection()
        BandNameRegistSection()
        BandRegionRegistSection()
        BandMemberRegistSection()
        BandIntroduceRegistSection()
        BandLinkRegistSection()
        CreateBandScreenButtonSection(navController)
    }
}

@Composable
fun BandImageRegistSection() {
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
fun BandNameRegistSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        TitleText(text = stringResource(R.string.band_name_regist_text_field_title))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.band_name_regist_text_field_title)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BandRegionRegistSection() {
    Column {
        TitleText(text = stringResource(R.string.band_region_regist_spinner_title))

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
fun BandMemberRegistSection() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(text = stringResource(R.string.band_member_add_section_title))
        MemberAddSection()
    }
}

@Composable
fun MemberAddSection() {
    val newBandMemberList = remember { mutableStateListOf<User>() }
    newBandMemberList.add(MockData.mockUsers[2])

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        newBandMemberList.forEach {
            MemberInfoItemView(it)
        }
        AddMemberButton()
    }
}

@Preview(showBackground = true)
@Composable
fun AddMemberButton() {
    Button(
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Gray
        ),
        onClick = {

        }
    ) {
        RightIconText(
            rowModifier = Modifier,
            text = stringResource(R.string.add_member_button_text),
            textStyle = MaterialTheme.typography.bodyLarge,
            textColor = White,
            textFontWeight = FontWeight.Bold
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null
            )
        }
    }
}

@Composable
fun MemberInfoItemView(user: User = MockData.mockUsers.random()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_10dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImageView(
            modifier = Modifier,
            imageResId = user.profileImageUrl,
            imageSize = dimensionResource(R.dimen.added_member_profile_image_size),
            imageDescription = user.nickName
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 10.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = user.nickName,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.6f)
            )

            Text(
                text = user.instrument.toString(),
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(0.4f)
            )
        }
    }
}


@Composable
fun BandIntroduceRegistSection() {
    Column {
        TitleText(text = stringResource(R.string.band_introduce_regist_text_field_title))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().heightIn(min = dimensionResource(R.dimen.band_introduce_text_field_min_height)),
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.band_introduce_regist_text_field_title)) }
        )
    }
}

@Composable
fun BandLinkRegistSection() {
    Column {
        TitleText(text = stringResource(R.string.band_link_regist_text_field_title))
        LinkTextField(platformTitle = stringResource(R.string.youtube_platform_text), platformIconRes = R.drawable.ic_youtube)
        LinkTextField(platformTitle = stringResource(R.string.instagram_platform_text), platformIconRes = R.drawable.ic_instagram)
        LinkTextField(platformTitle = stringResource(R.string.spotify_platform_text), platformIconRes = R.drawable.ic_spotify)
    }
}

@Composable
fun LinkTextField(
    modifier: Modifier = Modifier,
    platformTitle: String,
    platformIconRes: Int,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LeftIconText(
            rowModifier = Modifier.weight(0.3f),
            textModifier = Modifier,
            text = platformTitle,
            textStyle = MaterialTheme.typography.labelLarge,
        ) {
            Icon(
                painter = painterResource(platformIconRes),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.platform_icon_size))
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().weight(0.7f),
            maxLines = 1,
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.platform_text_field_label, platformTitle)) }
        )
    }

}

@Composable
fun CreateBandScreenButtonSection(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
    ) {
        CancelButton(Modifier.weight(0.5f), navController)
        CreateBandButton(Modifier.weight(0.5f))
    }
}

@Composable
fun CreateBandButton(modifier: Modifier = Modifier) {
    PrimaryColorRoundedButton(
        buttonModifier = modifier,
        butttonText = stringResource(R.string.create_band_button_text),
        onClick = {}
    )
}