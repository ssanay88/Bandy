package suhyeok.yang.shared.common.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.sample

fun Modifier.throttleClick(
    intervalMillis: Long = 1000L,
    onClick: () -> Unit
): Modifier = composed {

    // 클릭 이벤트 onClick을 위한 SharedFlow
    val clickFlow = remember {
        MutableSharedFlow<Unit>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    }

    // 가장 마지막으로 설정될 onClick
    val latestOnClick by rememberUpdatedState(onClick)

    LaunchedEffect(Unit) {
        clickFlow
            .sample(intervalMillis)
            .collect {
                latestOnClick()
            }
    }

    this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) {
        clickFlow.tryEmit(Unit)

    }

}