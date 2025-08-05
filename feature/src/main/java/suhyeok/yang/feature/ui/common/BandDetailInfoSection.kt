package suhyeok.yang.feature.ui.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil3.compose.AsyncImage
import com.yang.business.model.Band
import com.yang.business.model.User
import suhyeok.yang.feature.R
import suhyeok.yang.feature.ui.myband.BAND_DAYS_MAX_LINE
import suhyeok.yang.feature.ui.myband.BAND_FORMATION_DAY_MAX_LINE
import suhyeok.yang.feature.ui.myband.BAND_MEMBER_NAME_MAX_LINE
import suhyeok.yang.feature.ui.myband.BAND_NAME_MAX_LINE
import suhyeok.yang.shared.common.component.SectionTitleText
import suhyeok.yang.shared.common.component.WhiteRoundedCornerCard
import suhyeok.yang.shared.common.util.DateTimeUtils
import suhyeok.yang.shared.common.util.DateTimeUtils.createDateFromYMD
import suhyeok.yang.shared.ui.theme.Gray
import suhyeok.yang.shared.ui.theme.LightGray
import suhyeok.yang.shared.ui.theme.SuitFontFamily


@Composable
fun BandDetailInfoSection(band: Band) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGray)
            .verticalScroll(state = rememberScrollState())
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = band.coverImageUrl,
                contentDescription = stringResource(R.string.my_band_profile_image_descript),
                modifier = Modifier.fillMaxWidth().aspectRatio(1.8f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.my_band_detail_info_column_top_padding))
                    .padding(horizontal = dimensionResource(R.dimen.my_band_detail_info_column_horizontal_padding)),
            ) {
                BandMembersSection(band.members)
                BandPageSectionSpacer()
                BandDescriptionSection(band.bandDescription)
                BandPageSectionSpacer()
                BandGallery()
            }
        }
        BandInfoSection(band)
    }
}

@Composable
fun BandInfoSection(bandData: Band) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(R.dimen.my_band_info_section_top_padding))
            .padding(horizontal = dimensionResource(R.dimen.my_band_info_section_horizontal_padding))
    ) {
        WhiteRoundedCornerCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.my_band_info_section_card_height))
                .padding(top = dimensionResource(R.dimen.my_band_info_section_card_top_padding))
        ) {}
        BandProfileSection(bandData)
    }
}

@Composable
fun BandProfileSection(bandData: Band) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.my_band_profile_section_space))
    ) {
        BandProfileImage(bandData.bandProfileImageUrl)
        BandNameText(bandData.bandName)
        BandDaysSinceFormation()
        BandFormationDay()
    }
}

@Composable
fun BandProfileImage(bandImage: String) {
    AsyncImage(
        model = bandImage,
        contentDescription = "default profile image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(dimensionResource(R.dimen.my_band_profile_image_size))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.band_image_corner)))
    )
}

@Composable
fun BandNameText(bandName: String) {
    Text(
        text = bandName,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.ExtraBold,
        maxLines = BAND_NAME_MAX_LINE,
        overflow = TextOverflow.Ellipsis,
    )
}

// TODO 시간 저장 관련 내용 변경
@Composable
fun BandDaysSinceFormation() {
    val startDate = createDateFromYMD(2024, 4, 3)
    val endDate = createDateFromYMD(2025, 7, 4)
    val days = DateTimeUtils.calculateDaysBetweenDates(startDate, endDate)

    Text(
        text = stringResource(R.string.band_days_since_formation_text, days),
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        maxLines = BAND_DAYS_MAX_LINE,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun BandFormationDay() {
    Text(
        text = stringResource(
            R.string.band_formation_day_text,
            DateTimeUtils.formatDateToYYYYMMDD(createDateFromYMD(2024, 4, 3))
        ),
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.labelLarge,
        maxLines = BAND_FORMATION_DAY_MAX_LINE,
    )
}

@Composable
fun BandMembersSection(bandMemberList: List<User>) {
    SectionTitleText(title = stringResource(R.string.my_band_member_text))

    WhiteRoundedCornerCard(
        modifier = Modifier
            .heightIn(min = dimensionResource(R.dimen.my_band_member_section_min_height))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_10dp))
        ) {
            bandMemberList.forEachIndexed { index, member ->
                MemberItemView(index, member)
            }
        }
    }
}

@Composable
fun MemberItemView(index: Int = 0, user: User) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = dimensionResource(R.dimen.padding_vertical_5dp))
    ) {
        Text(
            text = (index + 1).toString(),
            modifier = Modifier.weight(0.05f),
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = user.instrument.toString(),
            modifier = Modifier.weight(0.3f),
            textAlign = TextAlign.Center,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodyMedium,
            color = Gray
        )
        Row(
            modifier = Modifier.weight(0.5f).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${user.nickName} (${user.userName})",
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = BAND_MEMBER_NAME_MAX_LINE,
                overflow = TextOverflow.Ellipsis
            )

            if (user.isLeader) {
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.band_member_leader_icon_space)))
                Icon(
                    painter = painterResource(R.drawable.ic_band_leader),
                    contentDescription = "band leader icon",
                    modifier = Modifier.size(dimensionResource(R.dimen.band_member_leader_icon_size)),
                    tint = Color.Unspecified
                )
            }
        }

    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.band_member_divider_height))
            .padding(horizontal = dimensionResource(R.dimen.band_member_divider_horizontal_padding))
            .background(LightGray)
    )
}


@Composable
fun BandDescriptionSection(bandDescription: String) {
    SectionTitleText(title = stringResource(R.string.band_introduce_text))

    WhiteRoundedCornerCard(
        modifier = Modifier
            .heightIn(min = dimensionResource(R.dimen.my_band_description_section_min_height))
            .fillMaxWidth()
    ) {
        Text(
            text = bandDescription,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(dimensionResource(R.dimen.padding_10dp))
        )
    }
}

@Composable
fun BandPageSectionSpacer() {
    Spacer(modifier = Modifier.fillMaxWidth().height(dimensionResource(R.dimen.space_5dp)))
}

@Composable
fun BandGallery() {
    // TODO 밴드 갤러리
    SectionTitleText(title = stringResource(R.string.band_gallery_text))

    LazyRow {

    }

}
