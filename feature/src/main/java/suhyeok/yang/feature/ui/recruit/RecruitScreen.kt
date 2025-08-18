package suhyeok.yang.feature.ui.recruit

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.yang.business.model.Band
import com.yang.business.enums.Instrument
import com.yang.business.enums.RecruitScreenTab
import com.yang.business.model.RecruitPosting
import com.yang.business.model.Region
import suhyeok.yang.feature.MockData
import suhyeok.yang.feature.R
import suhyeok.yang.shared.common.component.LightPrimaryRoundText
import suhyeok.yang.shared.common.component.LoadingScreen
import suhyeok.yang.shared.common.component.WhiteRoundedCornerCard
import suhyeok.yang.shared.common.util.DateTimeUtils
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.Tertiary

const val FIND_BAND_TAB_GRID_COUNT = 2
const val ITEM_VIEW_BAND_NAME_MAX_LINE = 1
const val ITEM_VIEW_BAND_CONTENT_MAX_LINES = 3
const val ITEM_VIEW_BAND_GENRES_MAX = 2
const val ITEM_VIEW_MEMBER_DESCRIPTION_MAX_LINES = 3
const val ITEM_VIEW_CHIPS_MAX_LINES = 2

@Composable
fun RecruitScreen(
    currentTab: RecruitScreenTab,
    onBandInfoClick: (String) -> Unit,
    onRecruitingMemberClick: (String) -> Unit
) {
    val viewModel: RecruitViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val currentTabIndex = when (currentTab) {
        RecruitScreenTab.BAND_RECRUIT_TAB -> 0
        RecruitScreenTab.MEMBER_RECRUIT_TAB -> 1
    }
    
    var selectedTabIndex by remember { mutableIntStateOf(currentTabIndex) }

    val tabIncludedData = listOf(
        stringResource(R.string.find_band_tab_text),
        stringResource(R.string.find_member_tab_text)
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TabLayout(selectedTabIndex, tabIncludedData) { index ->
            selectedTabIndex = index
        }
        when (selectedTabIndex) {
            0 -> {
                if (uiState.isRecruitingBandListLoading) {
                    LoadingScreen()
                } else {
                    FindBandScreen(uiState.recruitingBandList, onBandInfoClick)
                }

            }
            1 -> {
                if (uiState.isRecruitPostingListLoading) {
                    LoadingScreen()
                } else {
                    MemberRecruitingScreen(uiState.recruitPostingList, onRecruitingMemberClick)
                }

            }
        }
    }
}

@Composable
fun TabLayout(
    selectedTabIndex: Int,
    tabIncludedData: List<String> = listOf(),
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        modifier = Modifier,
        selectedTabIndex = selectedTabIndex,
        indicator = {
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(it[selectedTabIndex]),
                color = Primary,
                height = dimensionResource(R.dimen.recruit_tab_layout_indicator_height)
            )
        }
    ) {
        tabIncludedData.forEachIndexed { idx, item ->
            Tab(
                selectedContentColor = Primary,
                unselectedContentColor = Tertiary,
                selected = selectedTabIndex == idx,
                onClick = {
                    onTabSelected(idx)
                },
                text = {
                    Text(
                        text = item,
                        fontFamily = SuitFontFamily,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }

    }
}


@Composable
fun FindBandScreen(
    recruitingBandList: List<Band>,
    onBandInfoClick: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(FIND_BAND_TAB_GRID_COUNT)
    ) {
        items(recruitingBandList) { item ->
            FindBandItemView(item, onBandInfoClick)
        }
    }
}

@Composable
fun FindBandItemView(item: Band, onBandInfoClick: (String) -> Unit) {
    WhiteRoundedCornerCard(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.item_view_card_padding))
            .fillMaxWidth()
            .aspectRatio(0.55f)
            .throttleClick {
                onBandInfoClick(item.bandId)
            }
    ) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                model = item.bandProfileImageUrl,
                contentDescription = item.bandName,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.band_image_corner))),
                contentScale = ContentScale.FillBounds,
                placeholder = painterResource(R.drawable.bandy_logo_tertiary_color),
                error = painterResource(R.drawable.bandy_logo_tertiary_color)
            )

            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.find_band_item_view_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.find_member_item_view_space))
            ) {
                BandName(item.bandName)
                BandContent(item.bandDescription, Modifier.weight(1f))
                BandRegionAndGenres(item.region, item.preferredGenres)
            }
        }
    }

}

@Composable
fun BandName(bandName: String) {
    Text(
        text = bandName,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        maxLines = ITEM_VIEW_BAND_NAME_MAX_LINE,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(top = dimensionResource(R.dimen.find_band_item_view_band_name_top_padding))
    )
}

@Composable
fun BandContent(bandContent: String, modifier: Modifier = Modifier) {
    Text(
        text = bandContent,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = ITEM_VIEW_BAND_CONTENT_MAX_LINES,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

@Composable
fun BandRegionAndGenres(bandRegion: Region, preferredGenres: List<String>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        maxLines = ITEM_VIEW_CHIPS_MAX_LINES,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.find_band_item_view_band_tag_horizontal_space)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.find_band_item_view_band_tag_vertical_space))
    ) {
        LightPrimaryRoundText(bandRegion.sido)
        preferredGenres.shuffled().take(ITEM_VIEW_BAND_GENRES_MAX).forEach {
            LightPrimaryRoundText(it)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun FindBandItemViewPreview() {
    val item = MockData.mockBandList.filter { it.isRecruiting }[0]
    FindBandItemView(item) {}
}


@Composable
fun MemberRecruitingScreen(
    recruitPostingList: List<RecruitPosting>,
    onRecruitingMemberClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(recruitPostingList) { item ->
            MemberRecruitingItemView(item, onRecruitingMemberClick)
        }
    }
}

@Composable
fun MemberRecruitingItemView(item: RecruitPosting, onRecruitingMemberClick: (String) -> Unit) {
    WhiteRoundedCornerCard(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.item_view_card_padding))
            .throttleClick {
                onRecruitingMemberClick(item.recruitPostingId)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.find_member_item_view_height))
                .padding(dimensionResource(R.dimen.find_member_item_view_padding))
        ) {
            RecruitInstrumentText(item.targetInstrument)
            MemberRecruitingTitle(item.title)
            MemberRecruitingContent(item.content, Modifier.weight(1f))
            MemberRecruitingPostedBandDataRow(item)
        }
    }
}

@Composable
fun MemberRecruitingTitle(title: String) {
    Text(
        text = title,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.find_member_item_view_band_name_vertical_padding))
    )
}

@Composable
fun RecruitInstrumentText(instrument: Instrument) {
    LightPrimaryRoundText(stringResource(R.string.recruit_instrument_text, instrument))
}

@Composable
fun MemberRecruitingContent(content: String, modifier: Modifier) {
    Text(
        text = content,
        modifier = modifier.fillMaxWidth(),
        fontFamily = SuitFontFamily,
        maxLines = ITEM_VIEW_MEMBER_DESCRIPTION_MAX_LINES,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun MemberRecruitingPostedBandDataRow(item: RecruitPosting) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = item.postedBandImageUrl,
            contentDescription = item.postedBandName,
            modifier = Modifier
                .clip(RoundedCornerShape(dimensionResource(R.dimen.band_image_corner)))
                .size(dimensionResource(R.dimen.find_member_item_view_band_img_size))
        )

        Text(
            text = item.postedBandName,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.find_member_item_view_band_name_start_padding))
        )

        Text(
            text = stringResource(R.string.find_member_view_count_text, item.viewCount),
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.find_member_item_view_view_count_start_padding))
        )

        Text(
            text = DateTimeUtils.formatTimeAgo(item.createdAt),
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.find_member_item_view_posting_time_start_padding))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MemberRecruitingItemViewPreview() {
    val item = MockData.mockRecruitPostingList[0]
    MemberRecruitingItemView(item) {}
}
