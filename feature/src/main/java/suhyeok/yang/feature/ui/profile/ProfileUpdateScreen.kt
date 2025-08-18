package suhyeok.yang.feature.ui.profile

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.yang.business.enums.Instrument
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.CancelButton
import suhyeok.yang.feature.common.components.RegionSelectSection
import suhyeok.yang.shared.common.component.FilledButton
import suhyeok.yang.shared.common.component.OutlinedSpinner
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.common.util.toInstrument
import suhyeok.yang.shared.common.util.toStr


@Composable
fun ProfileUpdateScreen(
    onCancelClick: () -> Unit,
    onUpdateClick: () -> Unit
) {
    val viewModel: ProfileUpdateViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val instrumentUnselectedMessage = stringResource(R.string.profile_update_select_instrument_message)
    val nicknameEmptyMessage = stringResource(R.string.profile_update_input_nickname_message)

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
        ProfileImageUpdateSection(
            uiState.myProfileImageUrl,
            onProfileImageChanged = { selectedImageUrl ->
                viewModel.updateMyProfileImageUrl(selectedImageUrl)
            }
        )

        NicknameUpdateSection(
            uiState.myProfileNickname,
            onNicknameChanged = { inputNickname ->
                viewModel.updateMyProfileNickname(inputNickname)
            }
        )

        InstrumentUpdateSection(
            uiState.myProfileInstrument,
            onInstrumentChanged = { selectedInstrument ->
                viewModel.updateMyProfileInstrument(selectedInstrument)
            }
        )

        RegionSelectSection(
            uiState.myProfileSido,
            uiState.myProfileSigungu,
            onSidoChanged = { selectedSido ->
                viewModel.updateMyProfileSido(selectedSido)
            },
            onSigunguChanged = { selectedSigungu ->
                viewModel.updateMyProfileSigungu(selectedSigungu)
            }
        )

        IntroduceUpdateSection(
            uiState.myProfileIntroduce,
            onIntroduceChanged = { inputIntroduce ->
                viewModel.updateMyProfileIntroduce(inputIntroduce)
            }
        )

        UpdateButtonSection(
            onCancelClick = {
                viewModel.resetProfileChanges()
                onCancelClick()
            },
            onUpdateProfileClick = {
                when (viewModel.validateUpdatedProfileInfo()) {
                    is ProfileUpdateValidationResult.InstrumentUnselected -> {
                        Toast.makeText(context, instrumentUnselectedMessage, Toast.LENGTH_SHORT).show()
                    }
                    is ProfileUpdateValidationResult.NicknameEmpty -> {
                        Toast.makeText(context, nicknameEmptyMessage, Toast.LENGTH_SHORT).show()
                    }
                    is ProfileUpdateValidationResult.Success -> {
                        viewModel.updateMyProfile()
                        onUpdateClick()
                    }
                }

            })
    }
}

@Composable
fun ProfileImageUpdateSection(
    myProfileImageUri: String,
    onProfileImageChanged: (String) -> Unit
) {
    val context = LocalContext.current

    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val contentResolver = context.contentResolver
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                onProfileImageChanged(uri.toString())
            }
        }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.throttleClick {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        ) {
            AsyncImage(
                model = myProfileImageUri,
                contentDescription = stringResource(R.string.default_profile_image_descript),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(120.dp).align(Alignment.Center),
                placeholder = painterResource(R.drawable.ic_default_profile_img),
                error = painterResource(R.drawable.ic_default_profile_img)
            )
            Image(
                painter = painterResource(R.drawable.ic_change_profile_img),
                contentDescription = stringResource(R.string.change_profile_image_icon_descript),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(40.dp).align(Alignment.BottomEnd)
            )
        }
    }
}

@Composable
fun NicknameUpdateSection(
    myNickname: String,
    onNicknameChanged: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TitleText(text = stringResource(R.string.nickname))

        OutlinedTextField(
            value = myNickname,
            onValueChange = { newNickname ->
                onNicknameChanged(newNickname)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun InstrumentUpdateSection(
    myInstrument: Instrument,
    onInstrumentChanged: (Instrument) -> Unit
) {
    val instrumentList = Instrument.entries.map { it.toStr() }.toList()
    val selectedInstrumentIndex = instrumentList.indexOf(myInstrument.toStr())

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(text = stringResource(R.string.instrument))

        OutlinedSpinner(
            modifier = Modifier.fillMaxWidth(),
            items = instrumentList,
            selectedItemIdx = selectedInstrumentIndex,
            label = stringResource(R.string.instrument),
            onValueChange = { selectedInstrument ->
                onInstrumentChanged(selectedInstrument.toInstrument())
            }
        )
    }
}

@Composable
fun IntroduceUpdateSection(
    myIntroduce: String,
    onIntroduceChanged: (String) -> Unit
) {
    Column {
        TitleText(text = stringResource(R.string.introduce))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = myIntroduce,
            onValueChange = { inputIntroduce ->
                onIntroduceChanged(inputIntroduce)
            }
        )
    }
}

@Composable
fun UpdateButtonSection(onCancelClick: () -> Unit, onUpdateProfileClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
    ) {
        CancelButton(Modifier.weight(0.5f), onCancelClick)
        UpdateButton(Modifier.weight(0.5f), onUpdateProfileClick)
    }
}


@Composable
fun UpdateButton(modifier: Modifier = Modifier, onUpdateProfileClick: () -> Unit) {
    val context = LocalContext.current

    FilledButton(
        modifier = modifier,
        onClick = {
            onUpdateProfileClick()
        },
        content = {
            Text(text = stringResource(R.string.regist_text))
        }
    )
}