package suhyeok.yang.feature.ui.home

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.yang.business.model.Band
import com.yang.business.model.HomeTopBanner
import com.yang.business.model.Posting
import com.yang.business.enums.PostingType
import kotlinx.coroutines.delay
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.PostingItemView
import suhyeok.yang.shared.common.component.LoadingScreen
import suhyeok.yang.shared.common.component.SectionDivider
import suhyeok.yang.shared.common.component.SectionTitleText
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.common.util.toStr
import suhyeok.yang.shared.ui.theme.Gray
import suhyeok.yang.shared.ui.theme.LightGray
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.White

const val POSTING_TITLE_MAX_LINE = 1
const val TOP_BANNER_AUTO_SCROLL_DELAY = 5000L
const val TOP_BANNER_VIEW_PORT_SIZE = 3

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onPopularBandClick: (String) -> Unit,
    onPostingClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.overallLoading) {
        LoadingScreen()
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                TopBannerSection(
                    uiState.topBannerList
                )
            }

            item { SectionDivider() }

            item {
                PopularBandListSection(uiState.popularBandList, onPopularBandClick)
            }

            item { SectionDivider() }

            item {
                BestPostingSection(
                    bestPostingList = uiState.postingList.sortedByDescending { it.viewCount }
                        .take(5),
                    onPostingClick = onPostingClick
                )
            }

            item { SectionDivider() }

            // TODO 일정 갯수로 데이터 불러오도록 수정
            items(uiState.postingList) { posting ->
                PostingItemView(posting, onPostingClick)
            }
        }
    }


}

@Composable
fun TopBannerSection(
    itemList: List<HomeTopBanner>
) {
    val initialPage = (Int.MAX_VALUE / 2).let { it - (it % itemList.size) }
    val pagerState = rememberPagerState(initialPage = initialPage) { Int.MAX_VALUE }
    val infiniteItemList = { index: Int -> itemList[index % itemList.size] }

    LaunchedEffect(pagerState) {
        while (true) {
            delay(TOP_BANNER_AUTO_SCROLL_DELAY)

            if (!pagerState.isScrollInProgress) {
                val nextPage =
                    (pagerState.currentPage + 1).let { if (it !in 0..Int.MAX_VALUE) initialPage else it }
                runCatching {
                    pagerState.animateScrollToPage(nextPage)
                }.onFailure {
                    Log.e("error", it.message.toString())
                }
            }
        }
    }

    Box {
        HorizontalPager(
            modifier = Modifier.fillMaxWidth().height(dimensionResource(R.dimen.top_banner_height)),
            state = pagerState,
            beyondViewportPageCount = TOP_BANNER_VIEW_PORT_SIZE
        ) { index ->
            AsyncImage(
                model = infiniteItemList(index).bannerImageUrl,
                contentDescription = infiniteItemList(index).bannerDescription,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        }

        Box(
            modifier = Modifier
                .padding(
                    bottom = dimensionResource(R.dimen.top_banner_indicator_outside_padding),
                    end = dimensionResource(R.dimen.top_banner_indicator_outside_padding)
                )
                .align(Alignment.BottomEnd)
        ) {
            TopBannerIndicator(
                currentPage = pagerState.currentPage % itemList.size,
                totalPages = itemList.size,
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(R.dimen.top_banner_indicator_corner)))
                    .background(Gray.copy(alpha = 0.8f))
                    .padding(
                        vertical = dimensionResource(R.dimen.top_banner_indicator_inside_vertical_padding),
                        horizontal = dimensionResource(R.dimen.top_banner_indicator_inside_horizontal_padding)
                    )
            )
        }
    }
}

@Composable
fun TopBannerIndicator(
    currentPage: Int = 0,
    totalPages: Int = 0,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.top_banner_indicator_text_space))
    ) {
        TopBannerIndicatorText(
            text = "${currentPage + 1}",
            modifier = Modifier,
            fontWeight = FontWeight.Bold
        )

        TopBannerIndicatorText(
            text = "/",
            modifier = Modifier
        )

        TopBannerIndicatorText(
            text = totalPages.toString(),
            modifier = Modifier
        )
    }
}

@Composable
fun TopBannerIndicatorText(
    text: String,
    modifier: Modifier,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text,
        modifier = modifier,
        fontFamily = SuitFontFamily,
        fontWeight = fontWeight,
        style = MaterialTheme.typography.labelMedium,
        color = White
    )
}

@Composable
fun BestPostingSection(bestPostingList: List<Posting>, onPostingClick: (String) -> Unit) {
    // 인기 게시글
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(R.dimen.section_title_vertical_padding))
    ) {
        SectionTitleText(
            title = stringResource(R.string.best_posting_section_title),
        )

        var rank = 1
        bestPostingList.forEach { posting ->
            BestPostingItemView(rank++, posting, onPostingClick)
        }

    }
}

@Composable
fun BestPostingItemView(rank: Int, posting: Posting, onPostingClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.best_posting_item_view_vertical_padding))
            .throttleClick {
                onPostingClick(posting.postingId)
            }
    ) {
        Text(
            text = rank.toString(),
            modifier = Modifier.weight(0.05f),
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = posting.postingType.toStr(),
            modifier = Modifier.weight(0.15f),
            textAlign = TextAlign.Center,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodyMedium,
            color = Gray
        )
        Text(
            text = posting.title,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(0.5f),
            maxLines = POSTING_TITLE_MAX_LINE,
            overflow = TextOverflow.Ellipsis
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.best_posting_spacer_height))
            .padding(horizontal = dimensionResource(R.dimen.best_posting_spacer_horizontal_padding))
            .background(LightGray)
    )

}


@Composable
fun PopularBandListSection(
    popularBandList: List<Band>,
    onPopularBandClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.section_horizontal_padding))
    ) {
        SectionTitleText(title = stringResource(R.string.popular_band_section_title))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.popular_band_list_space))
        ) {
            items(popularBandList) { band ->
                PopularBandItemView(onPopularBandClick, band)
            }
        }
    }
}

@Composable
fun PopularBandItemView(onPopularBandClick: (String) -> Unit, band: Band) {
    Column(
        modifier = Modifier
            .width(dimensionResource(R.dimen.popular_band_item_view_width))
            .height(dimensionResource(R.dimen.popular_band_item_view_height))
            .throttleClick {
                // TODO 선택한 밴드 상세 페이지로 이동
                // BandDetailInfoPage(band)
                onPopularBandClick(band.bandId)
            }
    ) {
        AsyncImage(
            model = band.bandProfileImageUrl,
            contentDescription = band.bandName,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(dimensionResource(R.dimen.popular_band_item_view_round))),
            contentScale = ContentScale.FillBounds,
            placeholder = painterResource(R.drawable.bandy_logo_tertiary_color)
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = band.bandName,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.popular_band_item_view_text_top_padding))
            )
            Text(
                text = band.bandDescription,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.popular_band_item_view_text_top_padding))
            )
        }
    }
}
