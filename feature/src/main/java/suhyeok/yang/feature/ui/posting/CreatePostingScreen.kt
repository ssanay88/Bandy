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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yang.business.enums.PostingType
import suhyeok.yang.shared.common.component.RoundedCornerSpinner
import suhyeok.yang.shared.common.component.ThinDivider
import suhyeok.yang.shared.common.util.toStr
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.White

@Preview(showBackground = true)
@Composable
fun CreatePostingScreen(

) {
    Column(
        modifier = Modifier.fillMaxSize().padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectPostingTypeSection()

        InputPostingTitleSection()

        ThinDivider()

        InputPostingContentSection()
    }
}

@Composable
fun SelectPostingTypeSection() {
    val postingTypes = PostingType.entries.map { it.toStr() }.toList()

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        RoundedCornerSpinner(
            modifier = Modifier.width(150.dp).height(50.dp),
            items = postingTypes,
            selectedItemIdx = 0,
            onValueChange = {}
        )
    }
    

}

@Composable
fun InputPostingTitleSection() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        TextField(
            value = "",
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = White,
                unfocusedContainerColor = White
            ),
            placeholder = {
                Text(
                    text = "제목을 입력해주세요.",
                    fontFamily = SuitFontFamily,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        )
    }
}

@Composable
fun InputPostingContentSection() {
    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            modifier = Modifier.fillMaxSize(),
            value = "",
            onValueChange = {},
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = White,
                unfocusedContainerColor = White
            ),
            placeholder = {
                Text(
                    text = "내용을 입력해주세요.",
                    fontFamily = SuitFontFamily,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
    }
}