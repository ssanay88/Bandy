package suhyeok.yang.feature.ui.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.yang.business.enums.Gender
import com.yang.business.enums.Instrument
import com.yang.business.enums.SkillLevel
import com.yang.business.model.Region
import com.yang.business.model.User
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.BackButton
import suhyeok.yang.feature.common.components.RegionSelectSection
import suhyeok.yang.feature.common.components.rememberPhotoPicker
import suhyeok.yang.shared.common.component.FilledButton
import suhyeok.yang.shared.common.component.LoadingScreen
import suhyeok.yang.shared.common.component.OutlinedSpinner
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.common.util.toInstrument
import suhyeok.yang.shared.common.util.toStr
import suhyeok.yang.shared.ui.theme.BackgroundGray
import suhyeok.yang.shared.ui.theme.Black
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.TextGray
import suhyeok.yang.shared.ui.theme.White
import java.time.LocalDate
import java.time.ZoneId
import java.time.Instant

@Composable
fun ProfileRegScreen(
    newUserId: String,
    navController: NavController,
    onRegisterClick: () -> Unit
) {
    var selectedProfileImageUrl by remember { mutableStateOf("") }
    var selectedUserName by remember { mutableStateOf("") }
    var selectedUserNickname by remember { mutableStateOf("") }
    var selectedInstrument by remember { mutableStateOf(Instrument.VOCAL) }
    var selectedSkillLevel by remember { mutableStateOf(SkillLevel.BEGINNER) }
    var selectedSido by remember { mutableStateOf("") }
    var selectedSigungu by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf(Gender.MALE) }
    var selectedBirthDate by remember { mutableStateOf<LocalDate>(LocalDate.now()) }
    var regDescription by remember { mutableStateOf("") }

    val viewModel: ProfileRegViewModel = hiltViewModel()
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        uiState.overallLoading -> LoadingScreen()
    }

    fun validateUerProfile(): Boolean {
        when {
            selectedUserName.isEmpty() -> {
                Toast.makeText(context, "회원님 이름을 입력해주세요!", Toast.LENGTH_SHORT).show()
                return false
            }

            selectedUserNickname.isEmpty() -> {
                Toast.makeText(context, "닉네임을 입력해주세요!", Toast.LENGTH_SHORT).show()
                return false
            }

            regDescription.isEmpty() -> {
                Toast.makeText(context, "소개글을 입력해주세요!", Toast.LENGTH_SHORT).show()
                return false
            }

            else -> {
                return true
            }
        }
    }

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
            ProfileImageRegistSection(
                selectedProfileImgUrl = selectedProfileImageUrl,
                onProfileSelect = { newProfileImageUrl ->
                    selectedProfileImageUrl = newProfileImageUrl
                })

            UserNameRegistSection(
                userName = selectedUserName,
                onUserNameChanged = { userName ->
                    selectedUserName = userName
                }
            )

            NicknameRegistSection(
                nickname = selectedUserNickname,
                onNicknameChanged = { newNickname ->
                    selectedUserNickname = newNickname
                }
            )

            InstrumentRegistSection(
                onInstrumentChanged = { newInstrument ->
                    selectedInstrument = newInstrument
                }
            )

            SkillLevelRegistSection(
                selectedSkillLevel = selectedSkillLevel,
                onSkillLevelChanged = { newSkillLevel ->
                    selectedSkillLevel = newSkillLevel
                }
            )

            RegionSelectSection(
                onSidoChanged = { selectedSido = it },
                onSigunguChanged = { selectedSigungu = it }
            )

            GenderRegistSection(
                selectedGender = selectedGender,
                onGenderChanged = { newGender ->
                    selectedGender = newGender
                }
            )

            BirthdayPickerSection(
                selectedDate = selectedBirthDate,
                onDateSelected = { userBirthDate ->
                    selectedBirthDate = userBirthDate
                }
            )

            IntroduceRegistSection(
                regDescription = regDescription,
                onIntroduceChanged = { newIntroduce ->
                    regDescription = newIntroduce
                }
            )
        }

        ProfileRegScreenButtonSection(
            navController,
            onRegistClick = {
                if (validateUerProfile()) {
                    onRegisterClick()
                    val newUser = User(
                        userId = newUserId,
                        userName = selectedUserName,
                        nickName = selectedUserNickname,
                        profileImageUrl = selectedProfileImageUrl,
                        instrument = selectedInstrument,
                        userDescription = regDescription,
                        region = Region(sido = selectedSido, sigungu = selectedSigungu),
                        gender = selectedGender,
                        skillLevel = selectedSkillLevel,
                        birthDate = selectedBirthDate
                    )
                    viewModel.apply {
                        registerNewUser(newUser)
                        updateUserSession(newUser)
                        setLoggedIn()
                    }
                }
            })
    }
}

@Composable
fun ProfileImageRegistSection(selectedProfileImgUrl: String, onProfileSelect: (String) -> Unit) {

    val photoPicker = rememberPhotoPicker(onProfileSelect)

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.throttleClick {
                photoPicker.pickImage()
            }
        ) {
            AsyncImage(
                model = selectedProfileImgUrl,
                contentDescription = stringResource(R.string.default_profile_image_descript),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(120.dp).align(Alignment.Center),
                placeholder = painterResource(R.drawable.ic_default_profile_img),
                error = painterResource(R.drawable.ic_default_profile_img)
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
fun UserNameRegistSection(
    userName: String,
    onUserNameChanged: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TitleText(text = stringResource(R.string.user_name))

        OutlinedTextField(
            value = userName,
            onValueChange = { userName ->
                onUserNameChanged(userName)
            },
            placeholder = { Text(stringResource(R.string.user_name)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun NicknameRegistSection(
    nickname: String,
    onNicknameChanged: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TitleText(text = stringResource(R.string.nickname))

        OutlinedTextField(
            value = nickname,
            onValueChange = { newNickname ->
                onNicknameChanged(newNickname)
            },
            placeholder = { Text(stringResource(R.string.nickname)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstrumentRegistSection(
    onInstrumentChanged: (Instrument) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(text = stringResource(R.string.instrument))

        OutlinedSpinner(
            modifier = Modifier.fillMaxWidth(),
            items = Instrument.entries.map { it.toStr() },
            selectedItemIdx = 0,
            label = stringResource(R.string.instrument),
            onValueChange = { selectedInstrument ->
                onInstrumentChanged(selectedInstrument.toInstrument())
            }
        )
    }
}

@Composable
fun SkillLevelRegistSection(
    selectedSkillLevel: SkillLevel = SkillLevel.BEGINNER,
    onSkillLevelChanged: (SkillLevel) -> Unit
) {
    val selectedButtonColors = ButtonDefaults.buttonColors(
        containerColor = Primary,
        contentColor = White
    )
    val unSelectedButtonColors = ButtonDefaults.buttonColors(
        containerColor = BackgroundGray,
        contentColor = TextGray
    )

    Column {
        TitleText(text = stringResource(R.string.skill_level))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.profile_reg_screen_gender_button_height))
                .clip(RoundedCornerShape(dimensionResource(R.dimen.profile_reg_screen_gender_button_corner_radius)))
                .background(color = BackgroundGray)
        ) {
            RadioSelectButton(
                modifier = Modifier.fillMaxHeight().weight(1.2f),
                buttonText = SkillLevel.BEGINNER.toStr(),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = if (selectedSkillLevel == SkillLevel.BEGINNER) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    onSkillLevelChanged(SkillLevel.BEGINNER)
                }
            )
            RadioSelectButton(
                modifier = Modifier.fillMaxHeight().weight(1f),
                buttonText = SkillLevel.JUNIOR.toStr(),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = if (selectedSkillLevel == SkillLevel.JUNIOR) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    onSkillLevelChanged(SkillLevel.JUNIOR)
                }
            )
            RadioSelectButton(
                modifier = Modifier.fillMaxHeight().weight(1f),
                buttonText = SkillLevel.INTERMEDIATE.toStr(),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = if (selectedSkillLevel == SkillLevel.INTERMEDIATE) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    onSkillLevelChanged(SkillLevel.INTERMEDIATE)
                }
            )
            RadioSelectButton(
                modifier = Modifier.fillMaxHeight().weight(1f),
                buttonText = SkillLevel.ADVANCED.toStr(),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = if (selectedSkillLevel == SkillLevel.ADVANCED) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    onSkillLevelChanged(SkillLevel.ADVANCED)
                }
            )
            RadioSelectButton(
                modifier = Modifier.fillMaxHeight().weight(1.2f),
                buttonText = SkillLevel.EXPERT.toStr(),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = if (selectedSkillLevel == SkillLevel.EXPERT) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    onSkillLevelChanged(SkillLevel.EXPERT)
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GenderRegistSection(
    selectedGender: Gender = Gender.MALE,
    onGenderChanged: (Gender) -> Unit = {}
) {

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
                modifier = Modifier.fillMaxHeight().weight(0.5f),
                buttonText = stringResource(R.string.male),
                colors = if (selectedGender == Gender.MALE) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    onGenderChanged(Gender.MALE)
                }
            )
            RadioSelectButton(
                modifier = Modifier.fillMaxHeight().weight(0.5f),
                buttonText = stringResource(R.string.female),
                colors = if (selectedGender == Gender.FEMALE) selectedButtonColors else unSelectedButtonColors,
                onClick = {
                    onGenderChanged(Gender.FEMALE)
                }
            )
        }
    }
}

@Composable
fun RadioSelectButton(
    modifier: Modifier = Modifier,
    buttonText: String,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    colors: ButtonColors,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        colors = colors,
        contentPadding = PaddingValues(0.dp),
        onClick = { onClick() }
    ) {
        Text(
            text = buttonText,
            fontFamily = SuitFontFamily,
            style = textStyle,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdayPickerSection(
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit
) {
    Column {
        TitleText(text = stringResource(R.string.birthday))

        val datePickerState = rememberDatePickerState()
        val showDialog = remember { mutableStateOf(false) }

        Column {
            Box(
                modifier = Modifier
                    .border(
                        width = dimensionResource(R.dimen.profile_reg_screen_birth_border_width),
                        color = Black,
                        shape = RoundedCornerShape(dimensionResource(R.dimen.profile_reg_screen_birth_border_radius))
                    )
                    .padding(vertical = dimensionResource(R.dimen.profile_reg_screen_birth_border_padding))
                    .throttleClick {
                        showDialog.value = true
                    }
            ) {
                Text(
                    text = if (selectedDate == LocalDate.now()) stringResource(R.string.birthday_input_text) else selectedDate.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }


            if (showDialog.value) {
                DatePickerDialog(
                    onDismissRequest = { showDialog.value = false },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val millis = datePickerState.selectedDateMillis
                                if (millis != null) {
                                    val date = Instant.ofEpochMilli(millis)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                    onDateSelected(date)
                                }
                                showDialog.value = false
                            }
                        ) {
                            Text(stringResource(R.string.confirm_text))
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog.value = false }) {
                            Text(stringResource(R.string.cancel_text))
                        }
                    }
                ) {
                    DatePicker(state = datePickerState)
                }
            }
        }
    }

}

@Composable
fun IntroduceRegistSection(
    regDescription: String,
    onIntroduceChanged: (String) -> Unit
) {
    Column {
        TitleText(text = stringResource(R.string.introduce))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .heightIn(min = dimensionResource(R.dimen.profile_reg_screen_introduce_text_field_min_height)),
            value = regDescription,
            onValueChange = { changedDescription ->
                onIntroduceChanged(changedDescription)
            },
            placeholder = { Text(stringResource(R.string.introduce)) }
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
