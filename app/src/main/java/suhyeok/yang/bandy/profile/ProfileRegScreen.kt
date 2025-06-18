package suhyeok.yang.bandy.profile

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
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import suhyeok.yang.bandy.R
import suhyeok.yang.bandy.component.FilledButton
import suhyeok.yang.bandy.component.OutLinedButton
import suhyeok.yang.bandy.component.OutlinedSpinner
import suhyeok.yang.bandy.ui.theme.BandyTheme
import suhyeok.yang.bandy.utils.toSp

@Composable
fun ProfileRegScreen() {
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
        ProfileImageRegistSection()
        NicknameRegistSection()
        InstrumentRegistSection()
        RegionRegistSection()
        IntroduceRegistSection()
        LinkRegistSection()
        RegistButtonSection()

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

@Composable
fun RegionRegistSection() {
    Column {
        TitleText(text = stringResource(R.string.region))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
        ) {
            OutlinedSpinner(
                modifier = Modifier.weight(0.5f),
                items = listOf("서울특별시","경기도","경상북도","경상남도"),
                selectedItemIdx = 0,
                label = stringResource(R.string.region),
                onValueChange = {}
            )

            OutlinedSpinner(
                modifier = Modifier.weight(0.5f),
                items = listOf("홍대입구","신림","용산","잠실"),
                selectedItemIdx = 0,
                label = stringResource(R.string.region),
                onValueChange = {}
            )
        }
    }
}

@Composable
fun IntroduceRegistSection() {
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
fun LinkRegistSection() {

}

@Composable
fun RegistButtonSection() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
    ) {
        BackButton(Modifier.weight(0.5f))
        RegistButton(Modifier.weight(0.5f))
    }
}

@Composable
fun BackButton(modifier: Modifier = Modifier) {
    OutLinedButton(
        modifier = modifier,
        onClick = {},
        content = {
            Text(text = stringResource(R.string.back_text))
        }
    )
}

@Composable
fun RegistButton(modifier: Modifier = Modifier) {
    FilledButton(
        modifier = modifier,
        onClick = {},
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
        fontSize = dimensionResource(R.dimen.profile_reg_title_text_size).toSp(),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileRegScreenPreview() {
    BandyTheme {
        ProfileRegScreen()
    }
}