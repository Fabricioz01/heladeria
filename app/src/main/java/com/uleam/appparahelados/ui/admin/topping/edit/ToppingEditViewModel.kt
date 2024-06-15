package com.uleam.appparahelados.ui.admin.topping.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uleam.appparahelados.data.Topping.interfaces.ToppingRepository
import com.uleam.appparahelados.ui.admin.topping.entry.ToppingDetails
import com.uleam.appparahelados.ui.admin.topping.entry.ToppingUiState
import com.uleam.appparahelados.ui.admin.topping.entry.toToppingUiState
import com.uleam.appparahelados.ui.admin.topping.entry.totopping
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ToppingEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val toppingRepository: ToppingRepository
) : ViewModel() {

    var toppingUiState by mutableStateOf(ToppingUiState())
        private set

    private val toppingId: Int = checkNotNull(savedStateHandle[ToppingEditDestination.toppingIdArg])

    init {
        viewModelScope.launch {
            toppingUiState = toppingRepository.getToppingStream(toppingId)
                .filterNotNull()
                .first()
                .toToppingUiState(true)
        }
    }

    suspend fun updateTopping() {
        if (validateInput(toppingUiState.toppingDetails)) {
            toppingRepository.updateTopping(toppingUiState.toppingDetails.totopping())
        }
    }

    fun updateUiState(toppingDetails: ToppingDetails) {
        toppingUiState =
            ToppingUiState(toppingDetails = toppingDetails, isEntryValid = validateInput(toppingDetails))
    }

    private fun validateInput(uiState: ToppingDetails = toppingUiState.toppingDetails): Boolean {
        return with(uiState) {
            nombre.isNotBlank() && precio.isNotBlank() && cantidad.isNotBlank()
        }
    }
}