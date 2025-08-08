package suhyeok.yang.feature.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yang.business.model.Message
import com.yang.business.model.User
import suhyeok.yang.feature.R
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.ui.theme.Gray
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.White

@Composable
fun ChatRoomScreen(
    chatRoomId: String,
    currentUserId: String,
    viewModel: ChatRoomViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var messageInputText by remember { mutableStateOf("") }
    var inputHeight by remember { mutableStateOf(0) }

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

        Spacer(modifier = Modifier.width(8.dp))

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

@Composable
fun MessageItemView(isMine: Boolean, message: Message, sender: User?) {
    Column(

    ) {
        SenderInfoRow(sender)
        MessageRow(message)
        MessageInfoRow(message)
    }
}

@Composable
fun SenderInfoRow(sender: User?) {
    Row {
        Text(
            text = sender?.nickName ?: "Unknown User",
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MessageRow(message: Message) {
    Row {
        Text(
            text = message.content,
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun MessageInfoRow(message: Message) {
    Row {
        Text(
            text = message.timestamp.toString(),
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodySmall
        )
    }
}