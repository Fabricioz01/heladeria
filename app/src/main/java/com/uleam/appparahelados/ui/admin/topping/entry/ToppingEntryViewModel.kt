
package com.uleam.appparahelados.ui.admin.topping.entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.uleam.appparahelados.data.Topping.Topping
import com.uleam.appparahelados.data.Topping.interfaces.ToppingRepository
import java.text.NumberFormat

class ToppingEntryViewModel (private val toppingRepository: ToppingRepository) : ViewModel() {

    var toppingUiState by mutableStateOf(ToppingUiState())
        private set

    fun updateUiState(toppingDetails: ToppingDetails) {
        toppingUiState =
            ToppingUiState(toppingDetails = toppingDetails, isEntryValid = validateInput(toppingDetails))
    }

    private fun validateInput(uiState: ToppingDetails = toppingUiState.toppingDetails): Boolean {
        return with(uiState) {
            nombre.isNotBlank() && cantidad.isNotBlank() && precio.isNotBlank()
        }
    }
    suspend fun saveItem() {
        if (validateInput()) {
            toppingRepository.insertTopping(toppingUiState.toppingDetails.totopping())
        }
    }
}
data class ToppingUiState(
    val toppingDetails: ToppingDetails = ToppingDetails(),
    val isEntryValid: Boolean = false
)

data class ToppingDetails(
    val id: Int = 0,
    val nombre: String = "",
    val precio: String = "",
    val cantidad: String = ""
)


fun ToppingDetails.totopping(): Topping = Topping(
    id = id,
    nombre = nombre,
    precio = precio.toDoubleOrNull() ?: 0.0,
    cantidad = cantidad.toIntOrNull() ?:0
)

fun Topping.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(precio)
}

fun Topping.formatedCantidad(): String {
    return NumberFormat.getCurrencyInstance().format(cantidad)
}

fun Topping.toToppingUiState(isEntryValid: Boolean = false): ToppingUiState = ToppingUiState(
    toppingDetails = this.toToppingDetails(),
    isEntryValid = isEntryValid
)

fun Topping.toToppingDetails(): ToppingDetails = ToppingDetails(
    id = id,
    nombre = nombre,
    precio = precio.toString(),
    cantidad = cantidad.toString()
)
