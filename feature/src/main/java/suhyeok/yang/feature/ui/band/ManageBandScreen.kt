package suhyeok.yang.feature.ui.band

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.yang.business.common.UpdateRequestResult
import com.yang.business.model.ManageBandMenu
import com.yang.business.model.User
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.CancelButton
import suhyeok.yang.feature.common.components.PrimaryColorRoundedButton
import suhyeok.yang.feature.common.components.RegionSelectSection
import suhyeok.yang.feature.common.components.rememberPhotoPicker
import suhyeok.yang.shared.common.component.CircleImageView
import suhyeok.yang.shared.common.component.CustomAlertDialog
import suhyeok.yang.shared.common.component.LoadingCircularProgressIndicator
import suhyeok.yang.shared.common.component.LoadingScreen
import suhyeok.yang.shared.common.component.RightIconText
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.ui.theme.LightGray
import suhyeok.yang.shared.ui.theme.SuitFontFamily

/**
 * 밴드 관리 스크린
 */
@Preview(showBackground = true)
@Composable
fun ManageBandScreen(
    backToProfileScreen: () -> Unit = {},
    onCancelClick: () -> Unit = {}
) {
    var bandProfileImageUrl by remember { mutableStateOf("") }
    var bandName by remember { mutableStateOf("") }
    var selectedSido by remember { mutableStateOf("") }
    var selectedSigungu by remember { mutableStateOf("") }
    var bandMemberList by remember { mutableStateOf(emptyList<User>()) }
    var bandIntroduce by remember { mutableStateOf("") }

    val viewModel: ManageBandViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val changeLeaderResult by viewModel.changeLeaderResult.collectAsStateWithLifecycle()
    val removedMemberResult by viewModel.removedMemberResult.collectAsStateWithLifecycle()
    var showLoadingProgress by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadMyBand()
    }

    when {
        changeLeaderResult is UpdateRequestResult.Initial -> {
            showLoadingProgress = false
        }

        changeLeaderResult is UpdateRequestResult.Loading -> {
            showLoadingProgress = true
        }

        changeLeaderResult is UpdateRequestResult.Success -> {
            showLoadingProgress = false
            backToProfileScreen()
        }

        changeLeaderResult is UpdateRequestResult.Failure -> {
            showLoadingProgress = false
        }
    }

    when {
        removedMemberResult is UpdateRequestResult.Initial -> {
            showLoadingProgress = false
        }

        removedMemberResult is UpdateRequestResult.Loading -> {
            showLoadingProgress = true
        }

        removedMemberResult is UpdateRequestResult.Success -> {
            showLoadingProgress = false
        }

        removedMemberResult is UpdateRequestResult.Failure -> {
            showLoadingProgress = false
        }
    }

    if (uiState.overallLoading) {
        LoadingScreen()
    } else {
        Box {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(dimensionResource(R.dimen.padding_10dp))
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_24dp))
                ) {
                    BandProfileUpdateSection(
                        bandProfileImageUrl = uiState.bandProfileImageUrl,
                        onProfileSelect = { newProfileImageUrl ->
                            viewModel.updateProfileImageUrl(newProfileImageUrl)
                        }
                    )
                    BandNameUpdateSection(
                        bandName = uiState.bandName,
                        onNameChanged = { newName ->
                            viewModel.updateBandName(newName)
                        }
                    )
                    RegionSelectSection(
                        initSido = uiState.bandRegion.sido,
                        initSigungu = uiState.bandRegion.sigungu,
                        onSidoChanged = { viewModel.updateBandSido(it) },
                        onSigunguChanged = { viewModel.updateBandSigungu(it) }
                    )
                    BandMemberUpdateSection(
                        viewModel = viewModel,
                        bandMembers = uiState.bandMemberList
                    )
                    BandIntroduceUpdateSection(
                        bandIntroduce = uiState.bandIntroduce,
                        onIntroduceChanged = { newIntroduce ->
                            viewModel.updateBandIntroduce(newIntroduce)
                        }
                    )
                }

                ManageBandScreenButtonSection(
                    onCancelClick,
                    onUpdateBandInfoClick = {
                        viewModel.updateBandInfo()
                    }
                )
            }
            if (showLoadingProgress) Box(modifier = Modifier.align(Alignment.Center)) { LoadingCircularProgressIndicator() }
        }
    }
}

@Composable
fun BandProfileUpdateSection(
    bandProfileImageUrl: String,
    onProfileSelect: (String) -> Unit,
) {
    val photoPicker = rememberPhotoPicker(onProfileSelect)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.throttleClick {
                photoPicker.pickImage()
            }
        ) {
            AsyncImage(
                model = bandProfileImageUrl,
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
fun BandNameUpdateSection(
    bandName: String,
    onNameChanged: (String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            value = bandName,
            onValueChange = { onNameChanged(it) },
            placeholder = { Text(stringResource(R.string.band_name_regist_text_field_title)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BandMemberUpdateSection(
    viewModel: ManageBandViewModel,
    bandMembers: List<User>,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        bandMembers.forEach { member ->
            ManageBandMemberItemView(viewModel, member)
        }
    }
}

@Composable
fun ManageBandMemberItemView(viewModel: ManageBandViewModel, member: User) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_10dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImageView(
            modifier = Modifier,
            imageResId = member.profileImageUrl,
            imageSize = dimensionResource(R.dimen.added_member_profile_image_size),
            imageDescription = member.nickName,
            placeHolderResId = R.drawable.bandy_logo_tertiary_color,
            errorResId = R.drawable.bandy_logo_tertiary_color
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = dimensionResource(R.dimen.padding_10dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RightIconText(
                rowModifier = Modifier.weight(0.8f),
                text = "${member.nickName} (${member.userName})",
                textStyle = MaterialTheme.typography.titleLarge,
                textFontWeight = FontWeight.Bold,
                textModifier = Modifier
            ) {
                if (member.isLeader) {
                    Icon(
                        painter = painterResource(R.drawable.ic_band_leader),
                        contentDescription = "band leader icon",
                        modifier = Modifier.size(dimensionResource(R.dimen.manage_band_screen_leader_icon_size)),
                        tint = Color.Unspecified
                    )
                }
            }

            Text(
                text = member.instrument.toString(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(0.4f),
                textAlign = TextAlign.End
            )

            if (!member.isLeader) {
                Box() {
                    IconButton(
                        onClick = {
                            expanded = !expanded
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_setting),
                            contentDescription = stringResource(R.string.manage_member_icon_description),
                            modifier = Modifier
                                .padding(horizontal = dimensionResource(R.dimen.padding_5dp))
                                .size(dimensionResource(R.dimen.manage_member_icon_size)),
                            tint = Color.Unspecified
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier.background(LightGray),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        ManageMemberDropdownMenu(
                            ManageBandMenu(
                                title = stringResource(R.string.delegate_leader_text),
                                dialogTitle = stringResource(R.string.delegate_leader_text),
                                dialogDescription = stringResource(R.string.delegate_leader_dialog_description),
                                dialogConfirmButtonText = stringResource(R.string.delegate_leader_dialog_confirm_button_text),
                                dialogDismissButtonText = stringResource(R.string.cancel_text),
                                onConfirmClick = {
                                    viewModel.changeLeader(member.userId)
                                },
                                onDismissClick = {
                                    expanded = false
                                }
                            )
                        )
                        ManageMemberDropdownMenu(
                            ManageBandMenu(
                                title = stringResource(R.string.remove_member_text),
                                dialogTitle = stringResource(R.string.remove_member_text),
                                dialogDescription = stringResource(R.string.remove_member_dialog_description, member.nickName),
                                dialogConfirmButtonText = stringResource(R.string.remove_member_dialog_confirm_button_text),
                                dialogDismissButtonText = stringResource(R.string.cancel_text),
                                onConfirmClick = {
                                    viewModel.removeMember(member.userId)
                                },
                                onDismissClick = {
                                    expanded = false
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ManageMemberDropdownMenu(
    manageBandMenu: ManageBandMenu,
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        CustomAlertDialog(
            title = manageBandMenu.dialogTitle,
            description = manageBandMenu.dialogDescription,
            confirmButtonText = manageBandMenu.dialogConfirmButtonText,
            dismissButtonText = manageBandMenu.dialogDismissButtonText,
            onClickConfirm = { manageBandMenu.onConfirmClick() },
            onClickCancel = {
                showDialog = !showDialog
            }
        )
    }

    DropdownMenuItem(
        text = { Text(manageBandMenu.title) },
        modifier = Modifier.background(LightGray),
        onClick = {
            manageBandMenu.onConfirmClick()
            showDialog = !showDialog
        }
    )
}

@Composable
fun BandIntroduceUpdateSection(
    bandIntroduce: String,
    onIntroduceChanged: (String) -> Unit,
) {
    Column {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .heightIn(min = dimensionResource(R.dimen.band_introduce_text_field_min_height)),
            value = bandIntroduce,
            onValueChange = { onIntroduceChanged(it) },
            label = { Text(stringResource(R.string.band_introduce_regist_text_field_title)) }
        )
    }
}

@Composable
fun ManageBandScreenButtonSection(onCancelClick: () -> Unit, onUpdateBandInfoClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
    ) {
        CancelButton(Modifier.weight(0.5f), onCancelClick)
        UpdateBandButton(Modifier.weight(0.5f), onUpdateBandInfoClick)
    }
}

@Composable
fun UpdateBandButton(modifier: Modifier = Modifier, onUpdateBandInfoClick: () -> Unit) {
    PrimaryColorRoundedButton(
        buttonModifier = modifier,
        butttonText = stringResource(R.string.update_band_info_button_text),
        onClick = { onUpdateBandInfoClick() }
    )
}