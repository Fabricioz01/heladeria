package com.uleam.appparahelados.ui.Clasico.topppings


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import com.uleam.appparahelados.data.Topping.Topping
import com.uleam.appparahelados.data.Topping.interfaces.ToppingRepository

class UserToppingViewModel(private val toppingRepository: ToppingRepository) : ViewModel() {

    private val _cantidadToppings = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val cantidadToppings: StateFlow<Map<Int, Int>> = _cantidadToppings

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val toppingUiState: StateFlow<ToppingUiState> =
        toppingRepository.getAllToppingsStream()
            .map { ToppingUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ToppingUiState()
            )

    data class ToppingUiState(val toppingList: List<Topping> = listOf())

    fun incrementarCantidad(id: Int) {
        val current = _cantidadToppings.value.toMutableMap()
        current[id] = (current[id] ?: 0) + 1
        _cantidadToppings.value = current
    }

    fun decrementarCantidad(id: Int) {
        val current = _cantidadToppings.value.toMutableMap()
        val cantidadActual = current[id] ?: 0
        if (cantidadActual > 0) {
            current[id] = cantidadActual - 1
            _cantidadToppings.value = current
        }
    }
}
