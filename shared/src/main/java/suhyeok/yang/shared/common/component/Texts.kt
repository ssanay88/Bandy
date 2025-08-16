package suhyeok.yang.shared.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import suhyeok.yang.shared.R
import suhyeok.yang.shared.ui.theme.BackgroundGray
import suhyeok.yang.shared.ui.theme.Black
import suhyeok.yang.shared.ui.theme.LightPrimary
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.TextGray
import suhyeok.yang.shared.ui.theme.White

@Composable
fun LoginButtonText(
    modifier: Modifier = Modifier,
    text: String,
    fontColor: Color
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = fontColor
    )
}

@Composable
fun LightPrimaryRoundText(
    text: String
) {
    Text(
        text = text,
        color = White,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .background(
                color = LightPrimary,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 6.dp)
            .clip(RoundedCornerShape(20.dp))
    )
}

@Composable
fun GrayRoundedCornerText(
    text: String
) {
    Text(
        text = text,
        color = TextGray,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.labelSmall,
        modifier = Modifier
            .background(
                color = BackgroundGray,
                shape = RoundedCornerShape(dimensionResource(R.dimen.gray_rounded_corner_text_corner))
            )
            .padding(horizontal = dimensionResource(R.dimen.gray_rounded_corner_text_horizontal_padding))
            .clip(RoundedCornerShape(dimensionResource(R.dimen.gray_rounded_corner_text_corner)))
    )
}

@Composable
fun SectionTitleText(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(vertical = dimensionResource(R.dimen.section_title_text_vertical_padding))
    )
}

@Composable
fun LeftIconText(
    rowModifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = Black,
    textFontWeight: FontWeight = FontWeight.Normal,
    textModifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
) {
    Row(
        modifier = rowModifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.left_icon_text_horizontal_space)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Text(
            text = text,
            fontFamily = SuitFontFamily,
            style = textStyle,
            fontWeight = textFontWeight,
            color = textColor,
            modifier = textModifier
        )
    }
}

@Composable
fun RightIconText(
    rowModifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = Black,
    textFontWeight: FontWeight = FontWeight.Normal,
    textModifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
) {
    Row(
        modifier = rowModifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.left_icon_text_horizontal_space))
    ) {
        Text(
            text = text,
            fontFamily = SuitFontFamily,
            style = textStyle,
            fontWeight = textFontWeight,
            color = textColor,
            modifier = textModifier
        )
        icon()
    }
}

@OptIn(FlowPreview::class)
@Composable
fun DebounceOutlinedTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onDebounceValueChange: (String) -> Unit,
    debounceInterval: Long = 500L,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false,
) {
    val debounceFlow = remember { MutableStateFlow(value) }

    LaunchedEffect(onDebounceValueChange) {
        debounceFlow
            .debounce(debounceInterval)
            .distinctUntilChanged()
            .collect { debouncedValue ->
                onDebounceValueChange(debouncedValue)
            }
    }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
            debounceFlow.value = newValue
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = singleLine
    )

}
