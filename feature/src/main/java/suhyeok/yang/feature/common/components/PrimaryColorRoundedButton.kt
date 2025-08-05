package suhyeok.yang.feature.common.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import suhyeok.yang.shared.common.component.FilledButton
import suhyeok.yang.shared.ui.theme.SuitFontFamily

@Composable
fun PrimaryColorRoundedButton(
    buttonModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    butttonText: String,
    onClick: () -> Unit
    ) {
    FilledButton(
        modifier = buttonModifier,
        onClick = onClick,
        content = {
            Text(
                text = butttonText,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                modifier = textModifier
            )
        }
    )
}