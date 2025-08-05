package suhyeok.yang.feature.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.yang.business.model.Comment
import com.yang.business.model.Posting
import suhyeok.yang.feature.MockData
import suhyeok.yang.feature.R
import suhyeok.yang.shared.R as SharedR
import suhyeok.yang.shared.common.component.CircleImageView
import suhyeok.yang.shared.common.component.GrayRoundedCornerText
import suhyeok.yang.shared.common.component.LeftIconText
import suhyeok.yang.shared.common.component.SectionDivider
import suhyeok.yang.shared.common.component.SectionTitleText
import suhyeok.yang.shared.common.component.ThinDivider
import suhyeok.yang.shared.common.util.DateTimeUtils
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.TextGray


@Composable
fun PostingDetailScreen(postingId: String) {
    // TODO 뷰모델에서 밴드 데이터 가져오기
    val posting = MockData.postingList.random()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        PostingHeadSection(posting)
        SectionDivider()
        PostingContentSection(postingContent = posting.content)
        SectionDivider()
        PostingCommentSection()
    }

}

@Composable
fun PostingHeadSection(posting: Posting) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_10dp))
    ) {
        GrayRoundedCornerText(posting.postingType.toString())
        PostingTitleText(posting.title)
        PostingInfoRow(posting)
    }

}

@Composable
fun PostingTitleText(postingTitle: String) {
    Text(
        text = postingTitle,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_10dp))
    )
}

@Composable
fun PostingInfoRow(posting: Posting) {
    val postingAuthor = MockData.mockUsers.random()

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImageView(
            imageResId = postingAuthor.profileImageUrl,
            imageSize = dimensionResource(R.dimen.posting_detail_user_image_size)
        )
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_10dp))
        ) {
            Text(
                text = postingAuthor.nickName,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = TextGray
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_16dp))
            ) {
                PostingInfoIconText(
                    text = DateTimeUtils.formatTimeAgo(posting.createdAt),
                    iconResId = SharedR.drawable.ic_outline_time,
                    iconDescription = stringResource(R.string.posting_time_icon_description)
                )
                PostingInfoIconText(
                    text = posting.viewCount.toString(),
                    iconResId = SharedR.drawable.ic_outline_view_count,
                    iconDescription = stringResource(R.string.posting_view_count_icon_description)
                )
            }
        }
    }
}

@Composable
fun PostingInfoIconText(
    text: String,
    iconResId: Int,
    iconDescription: String = ""
) {
    LeftIconText(
        text = text,
        textStyle = MaterialTheme.typography.bodySmall,
        textColor = TextGray,
        textModifier = Modifier,
        icon = {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = iconDescription,
                tint = TextGray,
                modifier = Modifier.size(dimensionResource(R.dimen.posting_detail_info_icon_size))
            )
        }
    )
}


@Composable
fun PostingContentSection(postingContent: String) {
    Text(
        text = postingContent,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = dimensionResource(R.dimen.posting_detail_content_section_min_height))
            .padding(dimensionResource(R.dimen.padding_10dp))
    )
}

@Composable
fun PostingCommentSection() {
    LazyColumn {
        item {
            SectionTitleText(
                title = "댓글",
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_10dp))
            )
        }

        items(MockData.commentList) { comment ->
            ThinDivider()
            CommentItemView(comment)
        }

    }
}

@Composable
fun CommentItemView(comment: Comment) {
    val author = MockData.mockUsers.random()
    Row(
        modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_5dp))
    ) {
        CircleImageView(
            modifier = Modifier,
            imageResId = author.profileImageUrl,
            imageSize = dimensionResource(R.dimen.posting_detail_user_image_size),
            imageDescription = "",
            placeHolderResId = R.drawable.bandy_logo_tertiary_color,
            errorResId = R.drawable.bandy_logo_tertiary_color,
        )
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = dimensionResource(R.dimen.padding_5dp))
        ) {
            Text(
                text = author.nickName,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.bodySmall,
                color = TextGray
            )
            Spacer(Modifier.height(dimensionResource(R.dimen.posting_detail_content_section_author_info_space)))
            Text(
                text = DateTimeUtils.formatTimeAgo(comment.createdAt),
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.labelSmall,
                color = TextGray
            )
            Spacer(Modifier.height(dimensionResource(R.dimen.posting_detail_content_section_content_space)))
            Text(
                text = comment.content,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}