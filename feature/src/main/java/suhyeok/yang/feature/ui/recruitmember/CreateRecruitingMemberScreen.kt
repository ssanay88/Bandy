package suhyeok.yang.feature.ui.recruitmember

import android.widget.Toast
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.yang.business.enums.AgeGroup
import com.yang.business.enums.Gender
import com.yang.business.enums.Instrument
import com.yang.business.enums.SkillLevel
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.CancelButton
import suhyeok.yang.feature.common.components.RegionSelectSection
import suhyeok.yang.feature.ui.profile.RadioSelectButton
import suhyeok.yang.feature.ui.profile.TitleText
import suhyeok.yang.shared.common.component.FilledButton
import suhyeok.yang.shared.common.component.OutlinedSpinner
import suhyeok.yang.shared.common.component.SelectableChip
import suhyeok.yang.shared.common.util.toAgeGroup
import suhyeok.yang.shared.common.util.toInstrument
import suhyeok.yang.shared.common.util.toSkillLevel
import suhyeok.yang.shared.common.util.toStr
import suhyeok.yang.shared.ui.theme.BackgroundGray
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.TextGray
import suhyeok.yang.shared.ui.theme.White

@Composable
fun CreateRecruitingMemberScreen(
    viewModel: CreateRecruitingMemberViewModel,
    onCancelClick: () -> Unit,
    onCreateRecruitingClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val instrumentUnselectedMessage =
        stringResource(R.string.recruit_member_posting_select_instrument_message)
    val postingTitleIsEmptyMessage =
        stringResource(R.string.recruit_member_posting_input_title_message)
    val postingContentIsEmptyMessage =
        stringResource(R.string.recruit_member_posting_input_content_message)

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(dimensionResource(R.dimen.padding_10dp))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_24dp))
        ) {
            RecruitingInstrumentSection(
                onInstrumentChanged = { selectedInstrument ->
                    viewModel.setTargetInstrument(selectedInstrument)
                }
            )

            RecruitingAgeSection(
                updateTargetAgeGroup = { selectedAgeGroup ->
                    viewModel.updateAgeGroup(selectedAgeGroup)
                }
            )

            RegionSelectSection(
                onSidoChanged = { selectedSido -> viewModel.setTargetSido(selectedSido) },
                onSigunguChanged = { selectedSigungu -> viewModel.setTargetSigungu(selectedSigungu) }
            )

            RecruitingGenderSection(
                onGenderChanged = { selectedGender ->
                    viewModel.setTargetGender(selectedGender)
                }
            )

            RecruitingSkillLevelSection(
                onSkillLevelChanged = {

                }
            )
            RecruitingInfoTitleSection(
                recruitingInfoTitle = uiState.recruitingInfoTitle,
                onRecruitingInfoTitleChanged = { inputTitle ->
                    viewModel.setRecruitingInfoTitle(inputTitle)
                }
            )
            RecruitingInfoContentSection(
                recruitingInfoContent = uiState.recruitingInfoContent,
                onRecruitingInfoContentChanged = { inputContent ->
                    viewModel.setRecruitingInfoContent(inputContent)
                }
            )
        }

        CreateRecruitingMemberButton(
            onCancelClick = onCancelClick,
            onCreateRecruitingClick = {
                when (viewModel.validateRecruitingMemberPosting()) {
                    is RecruitPostingValidationResult.Success -> {
                        Toast.makeText(
                            context,
                            "성공",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.createRecruitingMemberPosting()
                        onCreateRecruitingClick()
                    }
                    is RecruitPostingValidationResult.InstrumentUnselected -> Toast.makeText(
                        context,
                        instrumentUnselectedMessage,
                        Toast.LENGTH_SHORT
                    ).show()

                    is RecruitPostingValidationResult.PostingTitleEmpty -> Toast.makeText(
                        context,
                        postingTitleIsEmptyMessage,
                        Toast.LENGTH_SHORT
                    ).show()

                    is RecruitPostingValidationResult.PostingContentEmpty -> Toast.makeText(
                        context,
                        postingContentIsEmptyMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }
}

@Composable
fun RecruitingInstrumentSection(
    onInstrumentChanged: (Instrument) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(text = stringResource(R.string.recruit_member_instrument_section_title))

        OutlinedSpinner(
            modifier = Modifier.fillMaxWidth(),
            items = Instrument.entries.map { it.toStr() },
            selectedItemIdx = 0,
            label = stringResource(R.string.instrument),
            onValueChange = { targetInstrument ->
                onInstrumentChanged(targetInstrument.toInstrument())
            }

        )
    }
}

@Composable
fun RecruitingAgeSection(
    updateTargetAgeGroup: (AgeGroup) -> Unit
) {
    Column {
        TitleText(text = stringResource(R.string.recruit_member_detail_info_target_age_title))
        AgeChipsRow(updateTargetAgeGroup)
    }
}

@Composable
fun AgeChipsRow(
    updateTargetAgeGroup: (AgeGroup) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AgeGroup.entries.map { it.toStr() }.forEach {
            CreateRecruitingMemberChip(
                ageGroupText = it,
                updateTargetAgeGroup = updateTargetAgeGroup
            )
        }
    }
}


@Composable
fun RecruitingGenderSection(
    onGenderChanged: (Gender) -> Unit
) {
    var targetGender by remember { mutableStateOf(Gender.ALL) }

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
                colors = if (targetGender == Gender.ALL) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    targetGender = Gender.ALL
                    onGenderChanged(Gender.ALL)
                }
            )
            RadioSelectButton(
                modifier = Modifier.fillMaxHeight().weight(1f),
                buttonText = stringResource(R.string.male),
                textStyle = MaterialTheme.typography.titleMedium,
                colors = if (targetGender == Gender.MALE) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    targetGender = Gender.MALE
                    onGenderChanged(Gender.MALE)
                }
            )
            RadioSelectButton(
                modifier = Modifier.fillMaxHeight().weight(1f),
                buttonText = stringResource(R.string.female),
                textStyle = MaterialTheme.typography.titleMedium,
                colors = if (targetGender == Gender.FEMALE) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    targetGender = Gender.FEMALE
                    onGenderChanged(Gender.FEMALE)
                }
            )
        }
    }
}


@Composable
fun CreateRecruitingMemberChip(
    ageGroupText: String,
    updateTargetAgeGroup: (AgeGroup) -> Unit
) {
    var selected by remember { mutableStateOf(false) }

    SelectableChip(
        text = ageGroupText,
        selected = selected,
        onClick = {
            selected = !selected
            updateTargetAgeGroup(ageGroupText.toAgeGroup())
        },
        modifier = Modifier
    )
}


@Composable
fun RecruitingSkillLevelSection(
    onSkillLevelChanged: (SkillLevel) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(text = stringResource(R.string.recruit_member_skill_level_section_title))

        OutlinedSpinner(
            modifier = Modifier.fillMaxWidth(),
            items = SkillLevel.entries.map { it.toStr() },
            selectedItemIdx = 0,
            label = stringResource(R.string.instrument),
            onValueChange = {
                onSkillLevelChanged(it.toSkillLevel())
            }

        )
    }
}

@Composable
fun RecruitingInfoTitleSection(
    recruitingInfoTitle: String,
    onRecruitingInfoTitleChanged: (String) -> Unit
) {
    Column {
        TitleText(text = stringResource(R.string.recruit_member_recruiting_info_title_section_title))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = recruitingInfoTitle,
            onValueChange = { onRecruitingInfoTitleChanged(it) },
            placeholder = {
                Text(text = stringResource(R.string.recruit_member_recruiting_info_title_section_content))
            }
        )
    }
}

@Composable
fun RecruitingInfoContentSection(
    recruitingInfoContent: String,
    onRecruitingInfoContentChanged: (String) -> Unit
) {
    Column {
        TitleText(text = stringResource(R.string.recruit_member_recruiting_info_content_section_title))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .heightIn(min = dimensionResource(R.dimen.recruit_member_recruiting_info_content_text_field_height)),
            value = recruitingInfoContent,
            onValueChange = { onRecruitingInfoContentChanged(it) },
            placeholder = {
                Text(text = stringResource(R.string.recruit_member_recruiting_info_content_section_content))
            }
        )
    }
}

@Composable
fun CreateRecruitingMemberButton(
    onCancelClick: () -> Unit,
    onCreateRecruitingClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_10dp)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
    ) {
        CancelButton(Modifier.weight(0.5f), onCancelClick)
        CreateRecruitingButton(Modifier.weight(0.5f), onCreateRecruitingClick)
    }
}

@Composable
fun CreateRecruitingButton(modifier: Modifier = Modifier, onCreateRecruitingClick: () -> Unit) {

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
