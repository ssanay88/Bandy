package suhyeok.yang.feature.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.yang.business.model.Posting
import suhyeok.yang.feature.R
import suhyeok.yang.feature.ui.home.POSTING_TITLE_MAX_LINE
import suhyeok.yang.shared.common.component.CircleImageView
import suhyeok.yang.shared.common.component.GrayRoundedCornerText
import suhyeok.yang.shared.common.util.DateTimeUtils
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.ui.theme.LightGray
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import java.time.LocalDateTime

@Composable
fun PostingItemView(
    posting: Posting,
    onPostingClick: (String) -> Unit
) {
    val author = posting.postingAuthorInfo

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .throttleClick {
                onPostingClick(posting.postingId)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.section_horizontal_padding))
                .padding(top = dimensionResource(R.dimen.posting_top_padding)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
        ) {
            GrayRoundedCornerText(posting.postingType.toString())

            Text(
                text = posting.title,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = POSTING_TITLE_MAX_LINE,
            )

            PostingInfoRow(
                author.authorProfileImageUrl,
                author.authorNickName,
                posting.viewCount,
                posting.createdAt
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.posting_spacer_height))
                .background(color = LightGray)
        )
    }
}

@Composable
fun PostingInfoRow(
    postingAuthorImage: String,
    postingAuthorNickName: String,
    postingViewCount: Int,
    createdAt: LocalDateTime
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = dimensionResource(R.dimen.space_8dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImageView(
            modifier = Modifier,
            imageResId = postingAuthorImage,
            imageSize = dimensionResource(R.dimen.posting_info_row_author_img_size),
            imageDescription = postingAuthorNickName
        )

        Text(
            text = postingAuthorNickName,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.posting_info_row_author_name_start_padding))
        )

        Text(
            text = stringResource(R.string.posting_info_view_count_text, postingViewCount),
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.posting_info_row_view_count_start_padding))
        )

        Text(
            text = DateTimeUtils.formatTimeAgo(createdAt),
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.posting_info_row_posting_time_start_padding))
        )
    }
}