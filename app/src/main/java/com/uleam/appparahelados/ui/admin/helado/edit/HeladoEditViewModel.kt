package com.uleam.appparahelados.ui.admin.helado.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uleam.appparahelados.data.Helado.interfaces.HeladoRepository
import com.uleam.appparahelados.ui.admin.helado.entry.HeladoDetails
import com.uleam.appparahelados.ui.admin.helado.entry.HeladoUiState
import com.uleam.appparahelados.ui.admin.helado.entry.toHeladoUiState
import com.uleam.appparahelados.ui.admin.helado.entry.toHelado
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class HeladoEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val heladoRepository: HeladoRepository
) : ViewModel() {

    var heladoUiState by mutableStateOf(HeladoUiState())
        private set

    private val itemId: Int = checkNotNull(savedStateHandle[HeladoEditDestination.heladoIdArg])

    init {
        viewModelScope.launch {
            heladoUiState = heladoRepository.getHeladoStream(itemId)
                .filterNotNull()
                .first()
                .toHeladoUiState(true)
        }
    }

    suspend fun updateHelado() {
        if (validateInput(heladoUiState.heladoDetails)) {
            heladoRepository.updateHelado(heladoUiState.heladoDetails.toHelado())
        }
    }

    fun updateUiState(heladoDetails: HeladoDetails) {
        heladoUiState =
            HeladoUiState(heladoDetails = heladoDetails, isEntryValid = validateInput(heladoDetails))
    }

    private fun validateInput(uiState: HeladoDetails = heladoUiState.heladoDetails): Boolean {
        return with(uiState) {
            nombre.isNotBlank() && precioBase.isNotBlank() && cantidad.isNotBlank()
        }
    }
}
