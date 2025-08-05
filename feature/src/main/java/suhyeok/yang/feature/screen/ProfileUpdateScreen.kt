package suhyeok.yang.feature.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.CancelButton
import suhyeok.yang.feature.common.components.RegionSelectSection
import suhyeok.yang.feature.ui.profile.TitleText
import suhyeok.yang.shared.common.component.FilledButton
import suhyeok.yang.shared.common.component.OutlinedSpinner


@Composable
fun ProfileUpdateScreen(
    navController: NavController,
    onUpdateClick: () -> Unit
) {

    var selectedSido by remember { mutableStateOf("") }
    var selectedSigungu by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = dimensionResource(R.dimen.profile_reg_screen_horizontal_padding),
                vertical = dimensionResource(R.dimen.profile_reg_screen_vertical_padding)
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        ProfileImageUpdateSection()
        NicknameUpdateSection()
        InstrumentUpdateSection()
        RegionSelectSection(
            onSidoChanged = { selectedSido = it },
            onSigunguChanged = { selectedSigungu = it }
        )
        IntroduceUpdateSection()
        LinkUpdateSection()
        UpdateButtonSection(navController, onUpdateClick)
    }
}

@Composable
fun ProfileImageUpdateSection() {
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
fun NicknameUpdateSection() {
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

@Composable
fun InstrumentUpdateSection() {
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

@Composable
fun IntroduceUpdateSection() {
    Column {
        TitleText(text = stringResource(R.string.introduce))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.introduce)) }
        )
    }
}

@Composable
fun LinkUpdateSection() {

}

@Composable
fun UpdateButtonSection(navController: NavController, onUpdateClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
    ) {
        CancelButton(Modifier.weight(0.5f), navController)
        UpdateButton(Modifier.weight(0.5f), onUpdateClick)
    }
}



@Composable
fun UpdateButton(modifier: Modifier = Modifier, onRegistClick: () -> Unit) {
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

@Preview(showBackground = true)
@Composable
fun ProfileUpdateScreenPreview() {
    ProfileUpdateScreen(
        navController = rememberNavController(),
        onUpdateClick = {}
    )
}