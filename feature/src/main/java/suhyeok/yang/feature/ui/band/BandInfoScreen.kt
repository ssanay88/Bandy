package suhyeok.yang.feature.ui.band

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import suhyeok.yang.feature.ui.common.BandDetailInfoSection
import suhyeok.yang.shared.common.component.LoadingScreen


@Composable
fun BandInfoScreen(
    viewModel: BandInfoViewModel,
    bandId: String
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadMyBand(bandId)
    }

    when {
        uiState.isBandLoading -> LoadingScreen()
        uiState.isBandLoaded -> uiState.band?.let { BandDetailInfoSection(it) }
    }

}