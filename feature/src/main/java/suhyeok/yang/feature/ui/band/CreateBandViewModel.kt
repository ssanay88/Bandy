package suhyeok.yang.feature.ui.band

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yang.business.common.DataResourceResult
import com.yang.business.model.Band
import com.yang.business.model.Region
import com.yang.business.model.User
import com.yang.business.repository.DataStoreRepository
import com.yang.business.usecase.band.BandUseCases
import com.yang.business.usecase.user.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateBandViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    private val bandUseCases: BandUseCases,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateBandUiState())
    val uiState = _uiState.asStateFlow()

    init {
        initBandDefaultValues()
    }

    private fun initBandDefaultValues() {
        viewModelScope.launch {
            val loggedUserId = dataStoreRepository.userId.first()
            _uiState.update {
                it.copy(bandLeaderId = loggedUserId)
            }

            userUseCases.readUser(loggedUserId).collectLatest { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Success -> {
                            it.copy(bandMemberList = it.bandMemberList + result.data)
                        }

                        else -> {
                            it
                        }
                    }
                }
            }
        }
    }

    private fun uiStateToBand(): Band =
        with(_uiState.value) {
            Band(
                bandId = "band_${UUID.randomUUID()}",
                bandName = bandName,
                bandProfileImageUrl = bandProfileImageUrl,
                coverImageUrl = bandCoverImageUrl,
                bandDescription = bandIntroduce,
                members = bandMemberList,
                region = Region(
                    sido = bandSido,
                    sigungu = bandSigungu
                ),
                leaderId = bandLeaderId,
                youtubeLink = bandYoutubeLink,
                instagramLink = bandInstagramLink,
                spotifyLink = bandSpotifyLink
            )
        }

    fun searchByNickname(nickname: String) {
        viewModelScope.launch {
            userUseCases.searchUserByNickname(nickname).collectLatest { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Loading -> it.copy(overallLoading = true)
                        is DataResourceResult.Success -> it.copy(
                            overallLoading = false,
                            matchedUsers = result.data
                        )

                        else -> {
                            it
                        }
                    }
                }
            }
        }
    }

    fun registerNewBand() {
        val newBand = uiStateToBand()

        viewModelScope.launch {
            bandUseCases.createBand(newBand).collectLatest { result ->
                _uiState.update {
                    when (result) {
                        is DataResourceResult.Loading -> it.copy(overallLoading = true)
                        is DataResourceResult.Success -> it.copy(overallLoading = false)
                        else -> {
                            it
                        }
                    }
                }
            }
        }
    }

    fun updateUserWithBand() {
        val newBand = uiStateToBand()

        viewModelScope.launch {
            dataStoreRepository.apply {
                setIsBand(true)
                setBandId(newBand.bandId)
            }

            userUseCases.readUser(dataStoreRepository.userId.first()).onEach { result ->
                when (result) {
                    is DataResourceResult.Success -> {
                        userUseCases.updateUser(
                            result.data.copy(
                                bandId = newBand.bandId,
                                isLeader = true
                            )
                        ).collect()
                    }

                    else -> {}
                }
            }.collect()
        }
    }

    fun setBandProfileImageUrl(url: String) {
        _uiState.update { it.copy(bandProfileImageUrl = url) }
    }

    fun setBandCoverImageUrl(url: String) {
        _uiState.update { it.copy(bandCoverImageUrl = url) }
    }

    fun setBandName(name: String) {
        _uiState.update { it.copy(bandName = name) }
    }

    fun setBandSido(sido: String) {
        _uiState.update { it.copy(bandSido = sido) }
    }

    fun setBandSigungu(sigungu: String) {
        _uiState.update { it.copy(bandSigungu = sigungu) }
    }

    fun addBandMember(user: User) {
        _uiState.update { it.copy(bandMemberList = it.bandMemberList + user) }
    }

    fun removeBandMember(user: User) {
        _uiState.update { it.copy(bandMemberList = it.bandMemberList - user) }
    }

    fun setBandIntroduce(introduce: String) {
        _uiState.update { it.copy(bandIntroduce = introduce) }
    }

    fun setBandYoutubeLink(link: String) {
        _uiState.update { it.copy(bandYoutubeLink = link) }
    }

    fun setBandInstagramLink(link: String) {
        _uiState.update { it.copy(bandInstagramLink = link) }
    }

    fun setBandSpotifyLink(link: String) {
        _uiState.update { it.copy(bandSpotifyLink = link) }
    }

    fun validateBandProfile(): CreateBandValidationResult =
        when {
            _uiState.value.bandName.isEmpty() -> CreateBandValidationResult.BandNameEmpty
            else -> CreateBandValidationResult.Success
        }

    fun resetCreateBandUiState() {
        _uiState.update {
            it.copy(
                bandProfileImageUrl = "",
                bandCoverImageUrl = "",
                bandName = "",
                bandSido = "",
                bandSigungu = "",
                bandMemberList = emptyList(),
                bandIntroduce = "",
                bandYoutubeLink = "",
                bandInstagramLink = "",
                bandSpotifyLink = ""
            )
        }
    }
}