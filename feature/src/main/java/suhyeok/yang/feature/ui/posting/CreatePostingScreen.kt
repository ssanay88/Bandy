package suhyeok.yang.feature.ui.posting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yang.business.enums.PostingType
import suhyeok.yang.feature.R
import suhyeok.yang.shared.common.component.RoundedCornerSpinner
import suhyeok.yang.shared.common.component.ThinDivider
import suhyeok.yang.shared.common.util.toPostingType
import suhyeok.yang.shared.common.util.toStr
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.White

@Composable
fun CreatePostingScreen(
    viewModel: CreatePostingViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectPostingTypeSection(
            onPostingTypeChanged = { selectedPostingType ->
                viewModel.selectPostingType(selectedPostingType)
            }
        )

        InputPostingTitleSection(
            postingTitle = uiState.postingTitle,
            onPostingTitleChanged = { newPostingTitle ->
                viewModel.setPostingTitle(newPostingTitle)
            }
        )

        ThinDivider()

        InputPostingContentSection(
            postingContent = uiState.postingContent,
            onPostingContentChanged = { newPostingContent ->
                viewModel.setPostingContent(newPostingContent)
            }
        )
    }
}

@Composable
fun SelectPostingTypeSection(
    onPostingTypeChanged: (PostingType) -> Unit
) {
    val postingTypes = PostingType.entries.map { it.toStr() }.toList()

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        RoundedCornerSpinner(
            modifier = Modifier.width(150.dp).height(50.dp),
            items = postingTypes,
            selectedItemIdx = 0,
            onValueChange = { selectedPostingType ->
                onPostingTypeChanged(selectedPostingType.toPostingType())
            }
        )
    }
    

}

@Composable
fun InputPostingTitleSection(
    postingTitle: String,
    onPostingTitleChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = postingTitle,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { inputTitle ->
                onPostingTitleChanged(inputTitle)
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = White,
                unfocusedContainerColor = White
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.create_posting_input_title_message),
                    fontFamily = SuitFontFamily,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        )
    }
}

@Composable
fun InputPostingContentSection(
    postingContent: String,
    onPostingContentChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            modifier = Modifier.fillMaxSize(),
            value = postingContent,
            onValueChange = { inputContent ->
                onPostingContentChanged(inputContent)
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = White,
                unfocusedContainerColor = White
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.create_posting_input_content_message),
                    fontFamily = SuitFontFamily,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
    }
}