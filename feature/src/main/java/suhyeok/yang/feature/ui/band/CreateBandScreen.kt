package suhyeok.yang.feature.ui.band

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.yang.business.model.Band
import com.yang.business.model.Region
import com.yang.business.model.User
import suhyeok.yang.feature.MockData
import suhyeok.yang.feature.R
import suhyeok.yang.feature.common.components.CancelButton
import suhyeok.yang.feature.common.components.PrimaryColorRoundedButton
import suhyeok.yang.feature.common.components.RegionSelectSection
import suhyeok.yang.feature.ui.profile.TitleText
import suhyeok.yang.shared.common.component.CircleImageView
import suhyeok.yang.shared.common.component.LeftIconText
import suhyeok.yang.shared.common.component.RightIconText
import suhyeok.yang.shared.common.util.throttleClick
import suhyeok.yang.shared.ui.theme.Primary
import suhyeok.yang.shared.ui.theme.SuitFontFamily
import suhyeok.yang.shared.ui.theme.White
import java.util.UUID

@Composable
fun CreateBandScreen(
    viewModel: CreateBandViewModel,
    onCancelClick: () -> Unit,
    onCreateBandClick: () -> Unit
) {
    var selectedBandProfileImageUrl by remember { mutableStateOf("") }
    var selectedBandCoverImageUrl by remember { mutableStateOf("") }
    var selectedBandName by remember { mutableStateOf("") }
    var selectedSido by remember { mutableStateOf("") }
    var selectedSigungu by remember { mutableStateOf("") }
    var selectedMemberList = remember { mutableStateListOf<User>() }
    var selectedBandIntroduce by remember { mutableStateOf("") }
    var selectedYoutubeLink by remember { mutableStateOf("") }
    var selectedInstagramLink by remember { mutableStateOf("") }
    var selectedSpotifyLink by remember { mutableStateOf("") }

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    fun validateBandProfile(): Boolean {
        return when {
            selectedBandName.isEmpty() -> {
                Toast.makeText(context, "회원님 이름을 입력해주세요!", Toast.LENGTH_SHORT).show()
                false
            }

            else -> true
        }
    }

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(dimensionResource(R.dimen.padding_10dp))
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_24dp))
        ) {
            BandImageRegistSection(
                selectedBandProfileImageUrl = selectedBandProfileImageUrl,
                onProfileSelect = { newBandProfileImageUrl ->
                    selectedBandProfileImageUrl = newBandProfileImageUrl
                }
            )

            BandCoverImageRegistSection(
                selectedBandCoverImageUrl = selectedBandCoverImageUrl,
                onCoverSelect = { newBandCoverImageUrl ->
                    selectedBandCoverImageUrl = newBandCoverImageUrl
                }
            )

            BandNameRegistSection(
                selectedBandName = selectedBandName,
                onBandNameChanged = { newBandName ->
                    selectedBandName = newBandName
                }
            )

            RegionSelectSection(
                onSidoChanged = { selectedSido = it },
                onSigunguChanged = { selectedSigungu = it }
            )

            BandMemberRegistSection(
                vm = viewModel,
                uiState = uiState,
                selectedMemberList = selectedMemberList,
                addNewMember = { newMember ->
                    selectedMemberList.add(newMember)
                }
            )

            BandIntroduceRegistSection(
                bandIntroduce = selectedBandIntroduce,
                onBandIntroduceChanged = { newBandIntroduce ->
                    selectedBandIntroduce = newBandIntroduce
                }
            )

            BandLinkRegistSection(
                youtubeLink = selectedYoutubeLink,
                onYoutubeLinkChanged = { newYoutubeLink ->
                    selectedYoutubeLink = newYoutubeLink
                },
                instagramLink = selectedInstagramLink,
                onInstagramLinkChanged = { newInstagramLink ->
                    selectedInstagramLink = newInstagramLink
                },
                spotifyLink = selectedSpotifyLink,
                onSpotifyLinkChanged = { newSpotifyLink ->
                    selectedSpotifyLink = newSpotifyLink
                }
            )
        }
        CreateBandScreenButtonSection(
            onCancelClick = onCancelClick,
            onCreateClick = {
                if (validateBandProfile()) {
                    onCreateBandClick()
                    val newBand = Band(
                        bandId = "band_${UUID.randomUUID()}",
                        bandName = selectedBandName,
                        bandProfileImageUrl = selectedBandProfileImageUrl,
                        coverImageUrl = selectedBandCoverImageUrl,
                        region = Region(sido = selectedSido, sigungu = selectedSigungu),
                        leaderId = "",
                        members = selectedMemberList,
                        bandDescription = selectedBandIntroduce,
                        youtubeLink = selectedYoutubeLink,
                        instagramLink = selectedInstagramLink,
                        spotifyLink = selectedSpotifyLink
                    )
                    viewModel.apply {
                        registerNewBand(newBand)
                        updateUserWithBand(newBand)
                    }
                }
            })
    }

}

@Preview(showBackground = true)
@Composable
fun BandImageRegistSection(
    selectedBandProfileImageUrl: String = "",
    onProfileSelect: (String) -> Unit = {}
) {
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let { onProfileSelect(it.toString()) }
        }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.throttleClick {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        ) {
            AsyncImage(
                model = selectedBandProfileImageUrl,
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

@Preview(showBackground = true)
@Composable
fun BandCoverImageRegistSection(
    selectedBandCoverImageUrl: String = "",
    onCoverSelect: (String) -> Unit = {}
) {
    val pickMedia =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let { onCoverSelect(it.toString()) }
        }

    Column {
        TitleText(text = stringResource(R.string.band_cover_image_regist_title))

        Box(modifier = Modifier) {
            AsyncImage(
                model = selectedBandCoverImageUrl,
                contentDescription = stringResource(R.string.default_profile_image_descript),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth().aspectRatio(1.7f)
            )
            Button(
                modifier = Modifier.padding(10.dp).align(Alignment.BottomEnd),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                ),
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }
            ) {
                Text(
                    text = stringResource(R.string.band_cover_image_change_text),
                    modifier = Modifier,
                    fontFamily = SuitFontFamily,
                    style = MaterialTheme.typography.bodyLarge,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }


}

@Composable
fun BandNameRegistSection(
    selectedBandName: String,
    onBandNameChanged: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TitleText(text = stringResource(R.string.band_name_regist_text_field_title))

        OutlinedTextField(
            value = selectedBandName,
            onValueChange = { bandName ->
                onBandNameChanged(bandName)
            },
            label = { Text(stringResource(R.string.band_name_regist_text_field_title)) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun BandMemberRegistSection(
    vm: CreateBandViewModel,
    uiState: CreateBandUiState,
    selectedMemberList: List<User>,
    addNewMember: (User) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleText(text = stringResource(R.string.band_member_add_section_title))
        MemberAddSection(vm, uiState, selectedMemberList, addNewMember)
    }
}

@Composable
fun MemberAddSection(
    vm: CreateBandViewModel,
    uiState: CreateBandUiState,
    selectedMemberList: List<User>,
    addNewMember: (User) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        selectedMemberList.forEach {
            MemberInfoItemView(it)
        }
        AddMemberButton(vm, uiState, selectedMemberList, addNewMember)
    }
}

@Composable
fun AddMemberButton(
    vm: CreateBandViewModel,
    uiState: CreateBandUiState,
    selectedMemberList: List<User>,
    addNewMember: (User) -> Unit
) {
    var showSearchMemberDialog by remember { mutableStateOf(false) }

    Button(
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary
        ),
        onClick = {
            showSearchMemberDialog = true
        }
    ) {
        RightIconText(
            rowModifier = Modifier,
            text = stringResource(R.string.add_member_button_text),
            textStyle = MaterialTheme.typography.bodyLarge,
            textColor = White,
            textFontWeight = FontWeight.Bold
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null
            )
        }
    }

    if (showSearchMemberDialog) {
        AddMemberDialog(
            viewModel = vm,
            uiState = uiState,
            onDismiss = { showSearchMemberDialog = false },
            onMemberSelected = { selectedMember ->
                if (!selectedMemberList.contains(selectedMember)) {
                    addNewMember(selectedMember)
                }
            }
        )
    }

}

@Composable
fun AddMemberDialog(
    viewModel: CreateBandViewModel,
    uiState: CreateBandUiState,
    onDismiss: () -> Unit,
    onMemberSelected: (User) -> Unit
) {
    var nickname by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.search_member_dialog_title)) },
        text = {
            Column {
                OutlinedTextField(
                    value = nickname,
                    onValueChange = {
                        nickname = it
                        viewModel.searchByNickname(nickname)
                    },
                    label = { Text(text = stringResource(R.string.search_member_dialog_search_text)) }
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.space_10dp)))

                uiState.matchedUsers.forEach { user ->
                    Text(
                        text = user.nickName,
                        modifier = Modifier
                            .fillMaxWidth()
                            .throttleClick {
                                onMemberSelected(user)
                                onDismiss()
                            }
                            .padding(dimensionResource(R.dimen.padding_5dp))
                    )
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.search_member_dialog_close_text))
            }
        }
    )
}


@Composable
fun MemberInfoItemView(user: User = MockData.mockUsers.random()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_10dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImageView(
            modifier = Modifier,
            imageResId = user.profileImageUrl,
            imageSize = dimensionResource(R.dimen.added_member_profile_image_size),
            imageDescription = user.nickName,
            placeHolderResId = R.drawable.bandy_logo_tertiary_color,
            errorResId = R.drawable.bandy_logo_tertiary_color
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(start = dimensionResource(R.dimen.padding_10dp)),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = user.nickName,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(0.6f)
            )

            Text(
                text = user.instrument.toString(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = SuitFontFamily,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(0.4f)
            )
        }
    }
}


@Composable
fun BandIntroduceRegistSection(
    bandIntroduce: String,
    onBandIntroduceChanged: (String) -> Unit
) {
    Column {
        TitleText(text = stringResource(R.string.band_introduce_regist_text_field_title))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .heightIn(min = dimensionResource(R.dimen.band_introduce_text_field_min_height)),
            value = bandIntroduce,
            onValueChange = { onBandIntroduceChanged(it) },
            label = { Text(stringResource(R.string.band_introduce_regist_text_field_title)) }
        )
    }
}

@Composable
fun BandLinkRegistSection(
    youtubeLink: String,
    onYoutubeLinkChanged: (String) -> Unit,
    instagramLink: String,
    onInstagramLinkChanged: (String) -> Unit,
    spotifyLink: String,
    onSpotifyLinkChanged: (String) -> Unit
) {
    Column {
        TitleText(text = stringResource(R.string.band_link_regist_text_field_title))
        LinkTextField(
            platformTitle = stringResource(R.string.youtube_platform_text),
            platformIconRes = R.drawable.ic_youtube,
            linkText = youtubeLink,
            onValueChange = onYoutubeLinkChanged
        )
        LinkTextField(
            platformTitle = stringResource(R.string.instagram_platform_text),
            platformIconRes = R.drawable.ic_instagram,
            linkText = instagramLink,
            onValueChange = onInstagramLinkChanged
        )
        LinkTextField(
            platformTitle = stringResource(R.string.spotify_platform_text),
            platformIconRes = R.drawable.ic_spotify,
            linkText = spotifyLink,
            onValueChange = onSpotifyLinkChanged
        )
    }
}

@Composable
fun LinkTextField(
    modifier: Modifier = Modifier,
    platformTitle: String,
    platformIconRes: Int,
    linkText: String,
    onValueChange: (String) -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LeftIconText(
            rowModifier = Modifier.weight(0.3f),
            textModifier = Modifier,
            text = platformTitle,
            textStyle = MaterialTheme.typography.labelLarge,
        ) {
            Icon(
                painter = painterResource(platformIconRes),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.platform_icon_size))
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().weight(0.7f),
            maxLines = 1,
            value = linkText,
            onValueChange = { onValueChange(it) },
            label = { Text(stringResource(R.string.platform_text_field_label, platformTitle)) }
        )
    }

}

@Composable
fun CreateBandScreenButtonSection(onCancelClick: () -> Unit, onCreateClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
    ) {
        CancelButton(Modifier.weight(0.5f), onCancelClick)
        CreateBandButton(Modifier.weight(0.5f), onCreateClick)
    }
}

@Composable
fun CreateBandButton(modifier: Modifier, onCreateClick: () -> Unit) {
    PrimaryColorRoundedButton(
        buttonModifier = modifier,
        butttonText = stringResource(R.string.create_band_button_text),
        onClick = { onCreateClick() }
    )
}