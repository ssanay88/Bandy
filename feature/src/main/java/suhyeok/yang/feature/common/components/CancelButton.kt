package suhyeok.yang.feature.common.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import suhyeok.yang.feature.R
import suhyeok.yang.shared.common.component.OutLinedButton
import suhyeok.yang.shared.ui.theme.SuitFontFamily

@Composable
fun CancelButton(modifier: Modifier = Modifier, onCancelClick: () -> Unit) {
    OutLinedButton(
        modifier = modifier,
        onClick = { onCancelClick() },
        content = {
            Text(
                text = stringResource(R.string.cancel_text),
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
        }
    )
}