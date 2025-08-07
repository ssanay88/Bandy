package suhyeok.yang.feature.ui.recruit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.yang.business.enums.AgeGroup
import com.yang.business.enums.Gender
import com.yang.business.enums.Instrument
import com.yang.business.enums.SkillLevel
import com.yang.business.model.Region
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.BackButton
import suhyeok.yang.feature.ui.posting.PostingInfoIconText
import suhyeok.yang.shared.R as SharedR
import suhyeok.yang.shared.common.component.CircleImageView
import suhyeok.yang.shared.common.component.FilledButton
import suhyeok.yang.shared.common.component.LightPrimaryRoundText
import suhyeok.yang.shared.common.component.LoadingScreen
import suhyeok.yang.shared.common.component.SectionDivider
import suhyeok.yang.shared.common.util.DateTimeUtils
import suhyeok.yang.shared.common.util.toStr
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.TextGray
import java.time.LocalDateTime

@Composable
fun RecruitingMemberScreen(
    recruitPostingId: String,
    navController: NavController,
    viewModel: RecruitingMemberViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadRecruitMemberPosting(recruitPostingId)
    }

    when {
        uiState.overallLoading -> LoadingScreen()

        else -> {
            Column {
                Column(modifier = Modifier.fillMaxSize().weight(1f)) {
                    RecruitingHeaderSection(
                        uiState.targetInstrument,
                        uiState.title,
                        uiState.postedBandImageUrl,
                        uiState.postedBandName,
                        uiState.createdAt,
                        uiState.viewCount
                    )

                    SectionDivider()

                    RecruitingDetailInfoSection(
                        uiState.targetInstrument,
                        uiState.targetAgeGroups,
                        uiState.targetGender,
                        uiState.targetRegion,
                        uiState.targetSkillLevel
                    )

                    SectionDivider()

                    RecruitingContentSection(uiState.content)

                    SectionDivider()
                }
                RecruitingMemberButtonSection(
                    navController = navController,
                    onApplyClick = {}
                )
            }
        }
    }

}

@Composable
fun RecruitingHeaderSection(
    recruitTargetInstrument: Instrument,
    recruitPostingTitle: String,
    postedBandImageUrl: String,
    postedBandName: String,
    recruitPostingCreatedAt: LocalDateTime,
    recruitPostingViewCount: Int,
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_10dp)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_10dp))
    ) {
        LightPrimaryRoundText(stringResource(R.string.recruit_instrument_text, recruitTargetInstrument.toStr()))
        Text(
            text = recruitPostingTitle,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.titleLarge
        )
        RecruitingAuthorBandInfoRow(
            bandProfileImageUrl = postedBandImageUrl,
            bandName = postedBandName,
            postingTime = recruitPostingCreatedAt,
            viewCount = recruitPostingViewCount
        )
    }
}

@Composable
fun RecruitingAuthorBandInfoRow(
    bandProfileImageUrl: String,
    bandName: String,
    postingTime: LocalDateTime,
    viewCount: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImageView(
            modifier = Modifier,
            imageResId = bandProfileImageUrl,
            imageSize = dimensionResource(R.dimen.recruiting_member_posting_band_img_size)
        )
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_10dp))
        ) {
            Text(
                text = bandName,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = TextGray
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_16dp))
            ) {
                PostingInfoIconText(
                    text = DateTimeUtils.formatTimeAgo(postingTime),
                    iconResId = SharedR.drawable.ic_outline_time,
                    iconDescription = stringResource(R.string.posting_time_icon_description)
                )
                PostingInfoIconText(
                    text = viewCount.toString(),
                    iconResId = SharedR.drawable.ic_outline_view_count,
                    iconDescription = stringResource(R.string.posting_view_count_icon_description)
                )
            }
        }
    }
}

@Composable
fun RecruitingDetailInfoSection(
    targetInstrument: Instrument,
    targetAgeGroups: List<AgeGroup>,
    targetGender: Gender,
    targetRegion: Region,
    targetSkillLevel: SkillLevel,
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        RecruitintInfoRow(infoTitle = stringResource(R.string.recruit_member_detail_info_target_instrument_title), infoContent = targetInstrument.toStr())
        RecruitintInfoRow(infoTitle = stringResource(R.string.recruit_member_detail_info_target_age_title), infoContent = targetAgeGroups.toStr())
        RecruitintInfoRow(infoTitle = stringResource(R.string.recruit_member_detail_info_target_gender_title), infoContent = targetGender.toStr())
        RecruitintInfoRow(infoTitle = stringResource(R.string.recruit_member_detail_info_target_region_title), infoContent = targetRegion.toString())
        RecruitintInfoRow(infoTitle = stringResource(R.string.recruit_member_detail_info_target_skill_level_title), infoContent = targetSkillLevel.toStr())
    }
}

@Composable
fun RecruitintInfoRow(
    infoTitle: String,
    infoContent: String
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = dimensionResource(R.dimen.padding_5dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = infoTitle,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = infoContent,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(0.7f)
        )
    }
}

@Composable
fun RecruitingContentSection(
    recruitPostingContent: String
) {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_10dp))
    ) {
        Text(
            text = recruitPostingContent,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun RecruitingMemberButtonSection(
    navController: NavController,
    onApplyClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_10dp)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
    ) {
        BackButton(Modifier.weight(0.5f), navController)
        ApplyButton(Modifier.weight(0.5f), onApplyClick)
    }
}



@Composable
fun ApplyButton(modifier: Modifier = Modifier, onApplyClick: () -> Unit) {
    val context = LocalContext.current

    FilledButton(
        modifier = modifier,
        onClick = {
            onApplyClick()
        },
        content = {
            Text(text = stringResource(R.string.apply_button_text))
        }
    )
}