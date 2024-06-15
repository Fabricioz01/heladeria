package com.uleam.appparahelados.ui.admin.helado.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uleam.appparahelados.data.Helado.interfaces.HeladoRepository
import com.uleam.appparahelados.ui.admin.helado.entry.HeladoDetails
import com.uleam.appparahelados.ui.admin.helado.entry.toHelado
import com.uleam.appparahelados.ui.admin.helado.entry.toHeladoDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class HeladoDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val heladoRepository: HeladoRepository
) : ViewModel() {

    private val heladoId: Int = checkNotNull(savedStateHandle[HeladoDetailsDestination.heladoIdArg])

    val uiState: StateFlow<HeladoDetailsUiState> =
        heladoRepository.getHeladoStream(heladoId)
            .filterNotNull()
            .map {
                HeladoDetailsUiState(outOfStock = it.cantidad <= 0, heladoDetails = it.toHeladoDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HeladoDetailsUiState()
            )

    /**
     * Reduces the item quantity by one and update the [HeladoRepository]'s data source.
     */
    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentItem = uiState.value.heladoDetails.toHelado()
            if (currentItem.cantidad > 0) {
                heladoRepository.updateHelado(currentItem.copy(cantidad = currentItem.cantidad - 1))
            }
        }
    }

    /**
     * Deletes the item from the [HeladoRepository]'s data source.
     */
    suspend fun deleteItem() {
        heladoRepository.deleteHelado(uiState.value.heladoDetails.toHelado())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HeladoDetailsUiState(
    val outOfStock: Boolean = true,
    val heladoDetails: HeladoDetails = HeladoDetails()
)
