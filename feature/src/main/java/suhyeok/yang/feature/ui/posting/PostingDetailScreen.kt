package suhyeok.yang.feature.ui.posting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yang.business.enums.PostingType
import com.yang.business.model.Comment
import com.yang.business.model.PostingAuthorInfo
import com.yang.business.model.User
import suhyeok.yang.feature.R
import suhyeok.yang.shared.R as SharedR
import suhyeok.yang.shared.common.component.CircleImageView
import suhyeok.yang.shared.common.component.GrayRoundedCornerText
import suhyeok.yang.shared.common.component.LeftIconText
import suhyeok.yang.shared.common.component.LoadingScreen
import suhyeok.yang.shared.common.component.SectionDivider
import suhyeok.yang.shared.common.component.SectionTitleText
import suhyeok.yang.shared.common.component.ThinDivider
import suhyeok.yang.shared.common.util.DateTimeUtils
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.ui.theme.Gray
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.TextGray
import suhyeok.yang.shared.ui.theme.White
import java.time.LocalDateTime
import java.util.UUID


@Composable
fun PostingDetailScreen(
    postingId: String,
    viewModel: PostingDetailViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentUserId by viewModel.currentUserId.collectAsStateWithLifecycle()
    val commentAuthorMap by viewModel.commentAuthorMap.collectAsStateWithLifecycle()
    // 댓글 입력
    var commentInputText by remember { mutableStateOf("") }
    // 댓글 입력창 높이 만큼 여백 추가
    var inputHeight by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.loadPostingDetail(postingId)
    }

    LaunchedEffect(Unit) {
        viewModel.getCurrentUserId()
    }

    when {
        uiState.overallLoading -> {
            LoadingScreen()
        }

        uiState.postingId.isNotEmpty() -> {
            Box() {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = with(LocalDensity.current) { inputHeight.toDp() })
                ) {
                    item {
                        PostingHeadSection(
                            uiState.postingType,
                            uiState.postingTitle,
                            uiState.postingAuthorInfo,
                            uiState.postingCreatedAt,
                            uiState.postingViewCount
                        )
                    }

                    item { SectionDivider() }

                    item { PostingContentSection(postingContent = uiState.postingContent) }

                    item { SectionDivider() }

                    item {
                        SectionTitleText(
                            title = "댓글",
                            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_10dp))
                        )
                    }

                    items(uiState.postingComments) { comment ->
                        LaunchedEffect(comment.authorId) {
                            viewModel.getCommentAuthorInfo(comment.authorId)
                        }
                        val author = commentAuthorMap[comment.authorId] ?: User()
                        ThinDivider()
                        CommentItemView(comment, author)
                    }
                }

                CommentInputRow(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .onGloballyPositioned { inputHeight = it.size.height },
                    nowComment = commentInputText,
                    onCommentTextChange = { newComment ->
                        commentInputText = newComment
                    },
                    onCommentSubmit = {
                        viewModel.commentSubmit(
                            Comment(
                                commentId = "comment_${UUID.randomUUID()}",
                                postingId = uiState.postingId,
                                authorId = currentUserId,
                                content = commentInputText,
                                createdAt = LocalDateTime.now(),
                                updatedAt = LocalDateTime.now()
                            )
                        )

                        commentInputText = ""
                    }
                )
            }

        }
    }
}

@Composable
fun PostingHeadSection(
    postingType: PostingType,
    postingTitle: String,
    postingAuthor: PostingAuthorInfo,
    postingCreatedAt: LocalDateTime,
    postingViewCount: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_10dp))
    ) {
        GrayRoundedCornerText(postingType.toString())
        PostingTitleText(postingTitle)
        PostingInfoRow(
            postingAuthor,
            postingCreatedAt,
            postingViewCount
        )
    }

}

@Composable
fun PostingTitleText(postingTitle: String) {
    Text(
        text = postingTitle,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_10dp))
    )
}

@Composable
fun PostingInfoRow(
    postingAuthor: PostingAuthorInfo,
    postingCreatedAt: LocalDateTime,
    postingViewCount: Int
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImageView(
            imageResId = postingAuthor.authorProfileImageUrl,
            imageSize = dimensionResource(R.dimen.posting_detail_user_image_size),
            placeHolderResId = R.drawable.bandy_logo_tertiary_color,
            errorResId = R.drawable.bandy_logo_tertiary_color
        )
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = dimensionResource(R.dimen.padding_10dp))
        ) {
            Text(
                text = postingAuthor.authorNickName,
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
                    text = DateTimeUtils.formatTimeAgo(postingCreatedAt),
                    iconResId = SharedR.drawable.ic_outline_time,
                    iconDescription = stringResource(R.string.posting_time_icon_description)
                )
                PostingInfoIconText(
                    text = postingViewCount.toString(),
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
fun CommentItemView(comment: Comment, author: User) {

    Row(
        modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_10dp))
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
            modifier = Modifier.fillMaxWidth()
                .padding(start = dimensionResource(R.dimen.padding_5dp))
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

@Composable
fun CommentInputRow(
    modifier: Modifier,
    nowComment: String,
    onCommentTextChange: (String) -> Unit,
    onCommentSubmit: () -> Unit
) {
    val isCommentEmpty = nowComment.isBlank()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = nowComment,
            onValueChange = { onCommentTextChange(it) },
            placeholder = { Text(text = stringResource(R.string.posting_comment_input_label)) },
            singleLine = true,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(if (isCommentEmpty) Gray else Primary)
                .throttleClick {
                    if (!isCommentEmpty) onCommentSubmit()
                }
        ) {
            Text(
                text = stringResource(R.string.posting_comment_input_button_text),
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = White,
                modifier = Modifier.padding(
                    vertical = dimensionResource(R.dimen.posting_detail_comment_input_button_vertical_padding),
                    horizontal = dimensionResource(R.dimen.posting_detail_comment_input_button_horizontal_padding)
                )
            )
        }
    }
}