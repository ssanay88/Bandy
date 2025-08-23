package suhyeok.yang.feature.ui.myband

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import suhyeok.yang.feature.R
import suhyeok.yang.feature.ui.common.BandDetailInfoSection
import suhyeok.yang.shared.common.component.FilledButton
import suhyeok.yang.shared.common.component.LoadingScreen
import suhyeok.yang.shared.ui.theme.SuitFontFamily

const val BAND_NAME_MAX_LINE = 1
const val BAND_DAYS_MAX_LINE = 1
const val BAND_FORMATION_DAY_MAX_LINE = 1
const val BAND_MEMBER_NAME_MAX_LINE = 1

@Composable
fun MyBandScreen(
    onSuggestFindBandClick: () -> Unit,
) {
    val viewModel: MyBandViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.checkHasBand()
    }

    when {
        uiState.isMyBandLoading -> LoadingScreen()
        uiState.hasBand -> {
            viewModel.loadMyBandData()
            uiState.myBand?.let { BandDetailInfoSection(it) }
        }
        else -> SuggestFindBandScreen(onSuggestFindBandClick)
    }
}

@Composable
fun SuggestFindBandScreen(onSuggestFindBandClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(dimensionResource(R.dimen.suggest_fine_band_screen_padding)),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.find_new_band_message),
            fontFamily = SuitFontFamily,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
        FilledButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.suggest_fine_band_button_height))
                .padding(top = dimensionResource(R.dimen.padding_10dp)),
            onClick = { onSuggestFindBandClick() }
        ) {
            Row {
                Text(
                    text = stringResource(R.string.find_new_band_button_text),
                    fontFamily = SuitFontFamily,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = stringResource(R.string.find_new_band_button_text),
                    modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_10dp))
                )
            }
        }
    }
}