package suhyeok.yang.feature.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.yang.business.model.ChatRoom
import suhyeok.yang.feature.R
import suhyeok.yang.shared.common.component.ThinDivider
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.ui.theme.Red
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.White

@Composable
fun ChatScreen(
    onChatRoomClick: (String, String) -> Unit
) {
    val viewModel: ChatViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadUsersChatRooms()
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(uiState.chatRooms) { room ->
            ChatRoomItemView(room, uiState.currentUserId, onChatRoomClick)
        }
    }
}

@Composable
fun ChatRoomItemView(
    room: ChatRoom,
    currentUserId: String,
    onChatRoomClick: (String, String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().throttleClick {
            onChatRoomClick(room.chatRoomId, currentUserId)
        }
    ) {
        Row {
            AsyncImage(
                model = room.roomImageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(dimensionResource(R.dimen.chat_room_item_view_thumbnail_image_size))
                    .padding(dimensionResource(R.dimen.chat_room_item_view_padding))
                    .clip(CircleShape),
                placeholder = painterResource(R.drawable.bandy_logo_tertiary_color),
                error = painterResource(R.drawable.bandy_logo_tertiary_color)
            )
            ChatRoomInfoView(room)
        }
        ThinDivider()
    }
}

@Composable
fun ChatRoomInfoView(room: ChatRoom) {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.chat_room_info_view_padding))
    ) {
        ChatRoomTitleView(room.chatRoomName)
        ChatRoomLastMessageView(room.lastMessage, room.unreadMessageCount)
    }
}

@Composable
fun ChatRoomTitleView(roomTitle: String?) {
    Text(
        text = roomTitle ?: "",
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun ChatRoomLastMessageView(lastMessage: String?, unreadMessageCount: Int) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = dimensionResource(R.dimen.chat_room_info_last_message_view_top_padding)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = lastMessage ?: "",
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )

        if (unreadMessageCount > 0) {
            Box(
                modifier = Modifier
                    .background(Red, shape = CircleShape)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .wrapContentSize()
            ) {
                Text(
                    text = "$unreadMessageCount",
                    color = White,
                    fontFamily = SuitFontFamily,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .defaultMinSize(minWidth = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }

}
