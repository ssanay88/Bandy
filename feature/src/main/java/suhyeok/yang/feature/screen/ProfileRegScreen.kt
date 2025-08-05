package suhyeok.yang.feature.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.yang.business.enums.Gender
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.BackButton
import suhyeok.yang.feature.common.components.RegionSelectSection
import suhyeok.yang.shared.common.component.FilledButton
import suhyeok.yang.shared.common.component.OutlinedSpinner
import suhyeok.yang.shared.ui.theme.BackgroundGray
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.TextGray
import suhyeok.yang.shared.ui.theme.White

@Composable
fun ProfileRegScreen(
    navController: NavController,
    onRegisterClick: () -> Unit
) {

    var selectedSido by remember { mutableStateOf("") }
    var selectedSigungu by remember { mutableStateOf("") }

    Column {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    horizontal = dimensionResource(R.dimen.profile_reg_screen_horizontal_padding),
                    vertical = dimensionResource(R.dimen.profile_reg_screen_vertical_padding)
                )
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_24dp)),
        ) {
            ProfileImageRegistSection()
            NicknameRegistSection()
            InstrumentRegistSection()
            RegionSelectSection(
                onSidoChanged = { selectedSido = it },
                onSigunguChanged = { selectedSigungu = it }
            )
            GenderRegistSection()
            IntroduceRegistSection()
        }
        ProfileRegScreenButtonSection(navController, onRegisterClick)
    }



}

@Composable
fun ProfileImageRegistSection() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.ic_default_profile_img),
                contentDescription = stringResource(R.string.default_profile_image_descript),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(120.dp).align(Alignment.Center)
            )
            Image(
                painter = painterResource(R.drawable.ic_change_profile_img),
                contentDescription = stringResource(R.string.change_profile_image_icon_descript),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(40.dp).align(Alignment.BottomEnd)
            )
        }
    }

}

@Composable
fun NicknameRegistSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        TitleText(text = stringResource(R.string.nickname))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.nickname)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstrumentRegistSection() {
    val instrumentList = listOf(
        "보컬",
        "기타",
        "베이스",
        "키보드",
        "드럼"
        )
    var selectedInstrument by remember { mutableStateOf(instrumentList[0]) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(text = stringResource(R.string.instrument))

        OutlinedSpinner(
            modifier = Modifier.fillMaxWidth(),
            items = instrumentList,
            selectedItemIdx = 0,
            label = stringResource(R.string.instrument),
            onValueChange = {
                selectedInstrument = it
            }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun GenderRegistSection() {
    var selectedButtonId by remember { mutableStateOf(Gender.MALE) }
    val selectedButtonColors = ButtonDefaults.buttonColors(
        containerColor = Primary,
        contentColor = White
    )
    val unSelectedButtonColors = ButtonDefaults.buttonColors(
        containerColor = BackgroundGray,
        contentColor = TextGray
    )

    Column {
        TitleText(text = stringResource(R.string.gender))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.profile_reg_screen_gender_button_height))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.profile_reg_screen_gender_button_corner_radius)))
                .background(color = BackgroundGray)
        ) {
            GenderSelectButton(
                modifier = Modifier.fillMaxHeight().weight(0.5f),
                buttonText = stringResource(R.string.male),
                colors = if (selectedButtonId == Gender.MALE) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    selectedButtonId = Gender.MALE
                }
            )
            GenderSelectButton(
                modifier = Modifier.fillMaxHeight().weight(0.5f),
                buttonText = stringResource(R.string.female),
                colors = if (selectedButtonId == Gender.FEMALE) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    selectedButtonId = Gender.FEMALE
                }
            )
        }
    }
}

@Composable
fun GenderSelectButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    colors: ButtonColors,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = colors,
        onClick = { onClick() }
    ) {
        Text(
            text = buttonText,
            fontFamily = SuitFontFamily,
            style = textStyle,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun IntroduceRegistSection() {
    Column {
        TitleText(text = stringResource(R.string.introduce))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().heightIn(min = dimensionResource(R.dimen.profile_reg_screen_introduce_text_field_min_height)),
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.introduce)) }
        )
    }
}

@Composable
fun ProfileRegScreenButtonSection(navController: NavController, onRegistClick: () -> Unit) {
    Row(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_10dp)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
    ) {
        BackButton(Modifier.weight(0.5f), navController)
        RegistButton(Modifier.weight(0.5f), onRegistClick)
    }
}

@Composable
fun RegistButton(modifier: Modifier = Modifier, onRegistClick: () -> Unit) {
    val context = LocalContext.current

    FilledButton(
        modifier = modifier,
        onClick = {
            onRegistClick()
        },
        content = {
            Text(text = stringResource(R.string.regist_text))
        }
    )
}

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        fontFamily = SuitFontFamily,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}
