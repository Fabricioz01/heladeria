
package com.uleam.appparahelados.ui.admin.topping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uleam.appparahelados.data.Topping.Topping
import com.uleam.appparahelados.data.Topping.interfaces.ToppingRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ToppingViewModel(toppingRepository: ToppingRepository): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    val toppingUiState: StateFlow<ToppingUiState> =
        toppingRepository.getAllToppingsStream().map { ToppingUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ToppingUiState()
            )
}

data class ToppingUiState(val toppingList: List<Topping> = listOf())
