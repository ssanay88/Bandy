package suhyeok.yang.feature.common.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import suhyeok.yang.feature.R
import suhyeok.yang.shared.common.component.OutLinedButton

@Composable
fun BackButton(modifier: Modifier = Modifier, navController: NavController) {
    OutLinedButton(
        modifier = modifier,
        onClick = { navController.popBackStack() },
        content = {
            Text(text = stringResource(R.string.back_text))
        }
    )
}