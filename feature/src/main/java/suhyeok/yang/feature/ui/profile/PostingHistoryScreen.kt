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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yang.business.model.Posting
import suhyeok.yang.feature.MockData
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.PostingItemView
import suhyeok.yang.feature.ui.recruit.TabLayout

@Composable
fun PostingHistoryScreen(
    onPostingClick: (String) -> Unit
) {
    val viewModel: PostingHistoryViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var selectedTabIndex by remember { mutableIntStateOf(0) }

    val tabIncludedData = listOf<String>(
        stringResource(R.string.my_posting_history_tab_text, uiState.myPostingList.size),
        stringResource(R.string.commented_posting_history_tab_text, uiState.commentedPostingList.size)
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TabLayout(selectedTabIndex, tabIncludedData) { index ->
            selectedTabIndex = index
        }
        when (selectedTabIndex) {
            0 -> MyPostingScreen(
                myPostings = uiState.myPostingList,
                onPostingClick = onPostingClick
            )
            1 -> CommentedPostingScreen(
                commentedPostings = uiState.commentedPostingList,
                onPostingClick = onPostingClick
            )
        }
    }
}

@Composable
fun MyPostingScreen(
    myPostings: List<Posting>,
    onPostingClick: (String) -> Unit
) {
    LazyColumn {
        items(myPostings) { posting ->
            PostingItemView(posting, onPostingClick)
        }
    }
}

@Composable
fun CommentedPostingScreen(
    commentedPostings: List<Posting>,
    onPostingClick: (String) -> Unit
) {
    LazyColumn {
        items(commentedPostings) { posting ->
            PostingItemView(posting,  onPostingClick)
        }
    }
}