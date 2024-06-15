

package com.uleam.appparahelados.ui.admin.topping.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uleam.appparahelados.data.Topping.interfaces.ToppingRepository
import com.uleam.appparahelados.ui.admin.topping.entry.ToppingDetails
import com.uleam.appparahelados.ui.admin.topping.entry.toToppingDetails
import com.uleam.appparahelados.ui.admin.topping.entry.totopping
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class ToppingDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val toppingRepository: ToppingRepository
    ) : ViewModel() {

    private val toppingId: Int = checkNotNull(savedStateHandle[ToppingDetailsDestination.toppingIdArg])


    val uiState: StateFlow<ToppingDetailsUiState> =
        toppingRepository.getToppingStream(toppingId)
            .filterNotNull()
            .map {
                ToppingDetailsUiState(outOfStock = it.cantidad <= 0, toppingDetails = it.toToppingDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ToppingDetailsUiState()
            )

    /**
     * Reduces the item quantity by one and update the [ItemsRepository]'s data source.
     */
    fun reduceQuantityByOne() {
        viewModelScope.launch {
            val currentItem = uiState.value.toppingDetails.totopping()
            if (currentItem.cantidad > 0) {
                toppingRepository.updateTopping(currentItem.copy(cantidad = currentItem.cantidad - 1))
            }
        }
    }

    /**
     * Deletes the item from the [ItemsRepository]'s data source.
     */
    suspend fun deleteItem() {
        toppingRepository.deleteTopping(uiState.value.toppingDetails.totopping())
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class ToppingDetailsUiState(
    val outOfStock: Boolean = true,
    val toppingDetails: ToppingDetails = ToppingDetails()
)
