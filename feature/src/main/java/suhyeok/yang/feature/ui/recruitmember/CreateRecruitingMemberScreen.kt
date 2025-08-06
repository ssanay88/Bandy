package suhyeok.yang.feature.ui.recruitmember

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.yang.business.enums.Gender
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.CancelButton
import suhyeok.yang.feature.common.components.RegionSelectSection
import suhyeok.yang.feature.ui.profile.RadioSelectButton
import suhyeok.yang.feature.ui.profile.TitleText
import suhyeok.yang.shared.common.component.FilledButton
import suhyeok.yang.shared.common.component.OutlinedSpinner
import suhyeok.yang.shared.common.component.SelectableChip
import suhyeok.yang.shared.ui.theme.BackgroundGray
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.TextGray
import suhyeok.yang.shared.ui.theme.White

@Composable
fun CreateRecruitingMemberScreen(
    viewModel: CreateRecruitingMemberViewModel,
    navController: NavController
) {

    var selectedSido by remember { mutableStateOf("") }
    var selectedSigungu by remember { mutableStateOf("") }

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(dimensionResource(R.dimen.padding_10dp))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_24dp))
        ) {
            RecruitingInstrumentSection()
            RecruitingAgeSection()
            RegionSelectSection(
                onSidoChanged = { selectedSido = it },
                onSigunguChanged = { selectedSigungu = it }
            )
            RecruitingGenderSection()
            RecruitingSkillLevelSection()
            RecruitingInfoContentSection()
        }
        CreateRecruitingMemberButton(
            navController = navController,
            onCreateRecruitingClick = { /*TODO 구인 공고 등록*/ }
        )
    }
}

@Composable
fun RecruitingInstrumentSection() {
    val instrumentList = listOf(
        "보컬",
        "기타",
        "베이스",
        "키보드",
        "드럼"
    )
    var recruitingInstrument by remember { mutableStateOf(instrumentList[0]) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(text = stringResource(R.string.recruit_member_instrument_section_title))

        OutlinedSpinner(
            modifier = Modifier.fillMaxWidth(),
            items = instrumentList,
            selectedItemIdx = 0,
            label = stringResource(R.string.instrument),
            onValueChange = {
                recruitingInstrument = it
            }

        )
    }
}

@Composable
fun RecruitingAgeSection() {
    Column {
        TitleText(text = stringResource(R.string.recruit_member_detail_info_target_age_title))
        AgeChipsRow()
    }
}

@Composable
fun AgeChipsRow() {
    val ageLists = listOf<String>(
        "10대",
        "20대",
        "30대",
        "40대",
        "50대 이상"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ageLists.forEach {
            CreateRecruitingMemberChip(text = it)
        }
    }
}



@Composable
fun RecruitingGenderSection() {
    var selectedButtonId by remember { mutableStateOf(Gender.ALL) }
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
            RadioSelectButton(
                modifier = Modifier.fillMaxHeight().weight(1f),
                buttonText = stringResource(R.string.any_gender),
                textStyle = MaterialTheme.typography.titleMedium,
                colors = if (selectedButtonId == Gender.ALL) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    selectedButtonId = Gender.ALL
                }
            )
            RadioSelectButton(
                modifier = Modifier.fillMaxHeight().weight(1f),
                buttonText = stringResource(R.string.male),
                textStyle = MaterialTheme.typography.titleMedium,
                colors = if (selectedButtonId == Gender.MALE) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    selectedButtonId = Gender.MALE
                }
            )
            RadioSelectButton(
                modifier = Modifier.fillMaxHeight().weight(1f),
                buttonText = stringResource(R.string.female),
                textStyle = MaterialTheme.typography.titleMedium,
                colors = if (selectedButtonId == Gender.FEMALE) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    selectedButtonId = Gender.FEMALE
                }
            )
        }
    }
}


@Composable
fun CreateRecruitingMemberChip(text: String) {
    var selected by remember { mutableStateOf(false) }

    SelectableChip(
        text = text,
        selected = selected,
        onClick = { selected = !selected },
        modifier = Modifier
    )
}


@Composable
fun RecruitingSkillLevelSection() {
    val skillLevelList = listOf(
        "입문",
        "초금",
        "중급",
        "고급",
        "전문가"
    )
    var recruitingInstrument by remember { mutableStateOf(skillLevelList[0]) }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(text = stringResource(R.string.recruit_member_skill_level_section_title))

        OutlinedSpinner(
            modifier = Modifier.fillMaxWidth(),
            items = skillLevelList,
            selectedItemIdx = 0,
            label = stringResource(R.string.instrument),
            onValueChange = {
                recruitingInstrument = it
            }

        )
    }
}

@Composable
fun RecruitingInfoContentSection() {
    Column {
        TitleText(text = stringResource(R.string.recruit_member_recruiting_info_content_section_title))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().heightIn(min = dimensionResource(R.dimen.recruit_member_recruiting_info_content_text_field_height)),
            value = "",
            onValueChange = {},
            label = { Text(stringResource(R.string.recruit_member_recruiting_info_content_section_content)) }
        )
    }
}

@Composable
fun CreateRecruitingMemberButton(
    navController: NavController,
    onCreateRecruitingClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_10dp)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
    ) {
        CancelButton(Modifier.weight(0.5f), navController)
        CreateRecruitingButton(Modifier.weight(0.5f), onCreateRecruitingClick)
    }
}

@Composable
fun CreateRecruitingButton(modifier: Modifier = Modifier, onCreateRecruitingClick: () -> Unit) {
    val context = LocalContext.current

    FilledButton(
        modifier = modifier,
        onClick = {
            onCreateRecruitingClick()
        },
        content = {
            Text(text = stringResource(R.string.regist_text))
        }
    )
}
