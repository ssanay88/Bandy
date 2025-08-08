package suhyeok.yang.feature.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import suhyeok.yang.feature.MockData
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.PostingItemView
import suhyeok.yang.feature.ui.recruit.TabLayout

@Composable
fun PostingHistoryScreen(
    viewModel: PostingHistoryViewModel
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabIncludedData = listOf<String>(
        // TODO 내 정보에 저장된 포스팅한 글, 댓글 단 글의 갯수를 추가
        stringResource(R.string.my_posting_history_tab_text, MockData.postingList.size),
        stringResource(R.string.commented_posting_history_tab_text, MockData.postingList.size)
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TabLayout(selectedTabIndex, tabIncludedData) { index ->
            selectedTabIndex = index
        }
        when (selectedTabIndex) {
            0 -> MyPostingScreen(
                onPostingClick = {}
            )
            1 -> CommentedPostingScreen(
                onPostingClick = {}
            )
        }
    }
}

@Composable
fun MyPostingScreen(
    onPostingClick: (String) -> Unit
) {
    LazyColumn {
        val postingList = MockData.postingList
        items(postingList) { posting ->
            PostingItemView(posting, onPostingClick)
        }
    }
}

@Composable
fun CommentedPostingScreen(
    onPostingClick: (String) -> Unit
) {
    LazyColumn {
        val postingList = MockData.postingList
        items(postingList) { posting ->
            PostingItemView(posting,  onPostingClick)
        }
    }
}