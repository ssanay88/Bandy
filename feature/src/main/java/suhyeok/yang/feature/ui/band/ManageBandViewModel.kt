package suhyeok.yang.feature.ui.band

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.common.UpdateRequestResult
import com.yang.business.model.Band
import com.yang.business.repository.DataStoreRepository
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.manageband.ManageBandUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageBandViewModel @Inject constructor(
    private val bandUseCases: BandUseCases,
    private val manageBandUseCases: ManageBandUseCases,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ManageBandUiState())
    val uiState: StateFlow<ManageBandUiState> = _uiState.asStateFlow()

    private val _changeLeaderResult = MutableStateFlow<UpdateRequestResult>(UpdateRequestResult.Initial)
    val changeLeaderResult: StateFlow<UpdateRequestResult> = _changeLeaderResult.asStateFlow()

    private val _removedMemberResult = MutableStateFlow<UpdateRequestResult>(UpdateRequestResult.Initial)
    val removedMemberResult: StateFlow<UpdateRequestResult> = _removedMemberResult.asStateFlow()

    private lateinit var loadedBandInfo: Band

    fun loadMyBand() {
        viewModelScope.launch {
            _uiState.update { it.copy(overallLoading = true) }
            val loggedUserBandId = dataStoreRepository.bandId.first()

            bandUseCases.readBand(loggedUserBandId).onEach { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            loadedBandInfo = result.data
                            it.copy(
                                overallLoading = false,
                                bandProfileImageUrl = result.data.bandProfileImageUrl,
                                bandName = result.data.bandName,
                                bandRegion = result.data.region,
                                bandMemberList = result.data.members,
                                bandIntroduce = result.data.bandDescription,
                            )
                        }

                        is DataResourceResult.Failure -> {
                            it
                        }

                        is DataResourceResult.Loading -> {
                            it.copy(overallLoading = true)
                        }

                        else -> {
                            it
                        }
                    }
                }
            }.collect()
        }
    }

    fun removeMember(userId: String) {
        viewModelScope.launch {
            _removedMemberResult.emit(UpdateRequestResult.Loading)

            manageBandUseCases.removeMember(loadedBandInfo.bandId, userId).collectLatest { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            _removedMemberResult.emit(UpdateRequestResult.Success)

                            loadedBandInfo.members = loadedBandInfo.members.filter { member -> member.userId != userId }

                            it.copy(
                                overallLoading = false,
                                bandMemberList = loadedBandInfo.members.filter { member -> member.userId != userId }
                            )
                        }

                        is DataResourceResult.Failure -> {
                            it.copy(overallLoading = false)
                        }

                        is DataResourceResult.Loading -> {
                            it.copy(overallLoading = true)
                        }

                        else -> it
                    }
                }
            }
        }

        _uiState.update {
            it.copy(bandMemberList = it.bandMemberList.filter { member -> member.userId != userId })
        }
    }

    fun changeLeader(newLeaderId: String) {
        viewModelScope.launch {
            _changeLeaderResult.emit(UpdateRequestResult.Loading)

            manageBandUseCases.handOverLeader(
                bandId = loadedBandInfo.bandId,
                prevLeaderId = loadedBandInfo.leaderId,
                newLeaderId = newLeaderId
            ).collectLatest {}

            dataStoreRepository.setIsLeader(false)

            _changeLeaderResult.emit(UpdateRequestResult.Success)
        }
    }

    fun updateBandInfo() {
        // 밴드 정보 수정
        viewModelScope.launch {
            bandUseCases.updateBand(
                loadedBandInfo.copy(
                    bandProfileImageUrl = _uiState.value.bandProfileImageUrl,
                    bandName = _uiState.value.bandName,
                    region = _uiState.value.bandRegion,
                    members = _uiState.value.bandMemberList,
                    bandDescription = _uiState.value.bandIntroduce,
                )
            ).collectLatest {}
        }
    }

    fun updateProfileImageUrl(imageUrl: String) {
        _uiState.update {
            it.copy(bandProfileImageUrl = imageUrl)
        }
    }

    fun updateBandName(name: String) {
        _uiState.update {
            it.copy(bandName = name)
        }
    }

    fun updateBandSido(sido: String) {
        _uiState.update {
            it.copy(bandRegion = it.bandRegion.copy(sido = sido))
        }
    }

    fun updateBandSigungu(sigungu: String) {
        _uiState.update {
            it.copy(bandRegion = it.bandRegion.copy(sigungu = sigungu))
        }
    }

    fun updateBandIntroduce(introduce: String) {
        _uiState.update {
            it.copy(bandIntroduce = introduce)
        }
    }
}