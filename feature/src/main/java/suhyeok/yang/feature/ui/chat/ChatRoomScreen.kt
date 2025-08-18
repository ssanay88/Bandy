package suhyeok.yang.feature.ui.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.yang.business.model.Message
import com.yang.business.model.User
import kotlinx.coroutines.launch
import suhyeok.yang.feature.MockData
import suhyeok.yang.feature.R
import suhyeok.yang.shared.common.component.CircleImageView
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.ui.theme.Black
import suhyeok.yang.shared.ui.theme.Gray
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.White
import java.time.format.DateTimeFormatter

@Composable
fun ChatRoomScreen(
    chatRoomId: String,
    currentUserId: String
) {
    val viewModel: ChatRoomViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var messageInputText by remember { mutableStateOf("") }
    var inputHeight by remember { mutableStateOf(0) }

    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.apply {
            observeChatRoom(chatRoomId)
            getChatRoomParticipantsInfo(chatRoomId)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = with(LocalDensity.current) { inputHeight.toDp() })
        ) {
            uiState.messages.forEach { message ->
                val sender = uiState.participants.find { it.userId == message.senderId }
                val isMine = currentUserId == message.senderId

                item {
                    MessageItemView(isMine, message, sender)
                }
            }
        }

        MessageInputRow(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .onGloballyPositioned { inputHeight = it.size.height },
            nowMessage = messageInputText,
            onMessageTextChange = { newComment ->
                messageInputText = newComment
            },
            onMessageSend = {
                viewModel.sendMessage(
                    chatRoomId,
                    currentUserId,
                    messageInputText
                )

                messageInputText = ""
                coroutineScope.launch {
                    scrollState.animateScrollToItem(uiState.messages.size - 1)
                }
            }
        )
    }
}

@Composable
fun MessageInputRow(
    modifier: Modifier,
    nowMessage: String,
    onMessageTextChange: (String) -> Unit,
    onMessageSend: () -> Unit
) {
    val isMessageEmpty = nowMessage.isBlank()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = nowMessage,
            onValueChange = { onMessageTextChange(it) }
        )

        Spacer(modifier = Modifier.width(dimensionResource(R.dimen.space_8dp)))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(if (isMessageEmpty) Gray else Primary)
                .throttleClick {
                    if (!isMessageEmpty) onMessageSend()
                }
        ) {
            Text(
                text = stringResource(R.string.send_message_button_text),
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

@Preview(showBackground = true)
@Composable
fun MessageItemView(isMine: Boolean = true, message: Message = MockData.generateMockMessages()[1], sender: User? = MockData.mockUsers[1]) {
    val bubbleRes = if (isMine) R.drawable.chat_bubble_mine else R.drawable.chat_bubble_other
    val textColor = if (isMine) White else Black

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {

            Image(
                rememberAsyncImagePainter(model = bubbleRes),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
            )

            Column(
                modifier = Modifier.padding(dimensionResource(R.dimen.chat_room_message_item_view_padding)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_10dp))
            ) {
                SenderInfoRow(sender, textColor)
                MessageRow(message, textColor)
                MessageInfoRow(message, textColor)
            }
        }
    }
}

@Composable
fun SenderInfoRow(sender: User?, textColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImageView(
            modifier = Modifier
                .wrapContentSize()
                .padding(end = dimensionResource(R.dimen.chat_room_message_item_view_sender_profile_end_padding)),
            imageResId = sender?.profileImageUrl ?: "",
            imageSize = dimensionResource(R.dimen.chat_room_message_item_view_sender_profile_size),
            placeHolderResId = R.drawable.ic_default_profile_img,
            errorResId = R.drawable.ic_default_profile_img

        )

        Text(
            text = sender?.nickName ?: "Unknown User",
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Composable
fun MessageRow(message: Message, textColor: Color) {
    Row {
        Text(
            text = message.content,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
    }
}

@Composable
fun MessageInfoRow(message: Message, textColor: Color) {
    Row {
        Text(
            text = message.timestamp.format(DateTimeFormatter.ofPattern(stringResource(R.string.message_timestamp_formatter))),
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.labelSmall,
            color = textColor
        )
    }
}