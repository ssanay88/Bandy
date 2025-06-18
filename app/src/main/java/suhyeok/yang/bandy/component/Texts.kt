package suhyeok.yang.bandy.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LoginButtonText(
    modifier: Modifier = Modifier,
    text: String,
    fontColor: Color
) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = FontWeight.Bold,
        color = fontColor
    )
}