package suhyeok.yang.feature.common.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import suhyeok.yang.feature.R
import suhyeok.yang.feature.ui.profile.TitleText
import suhyeok.yang.shared.common.component.OutlinedSpinner

@Composable
fun RegionSelectSection(
    initSido: String = "서울특별시",
    initSigungu: String = "전체",
    onSidoChanged: (String) -> Unit,
    onSigunguChanged: (String) -> Unit
) {
    Column {
        TitleText(text = stringResource(R.string.region))

        val context = LocalContext.current

        fun getStringRes(ResId: Int): String = context.getString(ResId)

        fun getSigunguArrayResId(selectedSido: String): Int {
            return when (selectedSido) {
                getStringRes(R.string.seoul) -> R.array.seoul
                getStringRes(R.string.gyeonggido) -> R.array.gyeonggido
                getStringRes(R.string.incheon) -> R.array.incheon
                getStringRes(R.string.sejong) -> R.array.sejong
                getStringRes(R.string.gangwondo) -> R.array.gangwondo
                getStringRes(R.string.chungcheongbukdo) -> R.array.chungcheongbukdo
                getStringRes(R.string.chungcheongnamdo) -> R.array.chungcheongnamdo
                getStringRes(R.string.daejeon) -> R.array.daejeon
                getStringRes(R.string.gyeongsangbukdo) -> R.array.gyeongsangbukdo
                getStringRes(R.string.daegu) -> R.array.daegu
                getStringRes(R.string.gyeongsangnamdo) -> R.array.gyeongsangnamdo
                getStringRes(R.string.ulsan) -> R.array.ulsan
                getStringRes(R.string.busan) -> R.array.busan
                getStringRes(R.string.jeollabukdo) -> R.array.jeollabukdo
                getStringRes(R.string.jeollanamdo) -> R.array.jeollanamdo
                getStringRes(R.string.gwangju) -> R.array.gwangju
                else -> R.array.jeju
            }
        }

        var selectedSigungu by remember { mutableStateOf(initSigungu) }
        val sidoList = context.resources.getStringArray(R.array.sido_List).toList()
        var sigunguList by remember { mutableStateOf(context.resources.getStringArray(getSigunguArrayResId(initSido)).toList()) }

        LaunchedEffect(Unit) {
            onSidoChanged(initSido)
            onSigunguChanged(initSigungu)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space_8dp))
        ) {
            OutlinedSpinner(
                modifier = Modifier.weight(0.5f),
                items = sidoList,
                selectedItemIdx = sidoList.indexOf(initSido),
                label = stringResource(R.string.sido),
                onValueChange = {newSido ->
                    onSidoChanged(newSido)
                    sigunguList = context.resources.getStringArray(getSigunguArrayResId(newSido)).toList()
                    selectedSigungu = sigunguList.first()
                    onSigunguChanged(selectedSigungu)
                }
            )

            OutlinedSpinner(
                modifier = Modifier.weight(0.5f),
                items = sigunguList,
                selectedItemIdx = sigunguList.indexOf(initSigungu),
                label = stringResource(R.string.sigungu),
                onValueChange = {
                    onSigunguChanged(it)
                }
            )
        }
    }
}