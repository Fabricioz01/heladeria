package com.uleam.appparahelados.ui.admin.helado.entry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.uleam.appparahelados.data.Helado.Helado
import com.uleam.appparahelados.data.Helado.interfaces.HeladoRepository
import java.text.NumberFormat

class HeladoEntryViewModel(private val heladoRepository: HeladoRepository) : ViewModel() {

    var heladoUiState by mutableStateOf(HeladoUiState())
        private set

    fun updateUiState(heladoDetails: HeladoDetails) {
        heladoUiState =
            HeladoUiState(heladoDetails = heladoDetails, isEntryValid = validateInput(heladoDetails))
    }

    private fun validateInput(uiState: HeladoDetails = heladoUiState.heladoDetails): Boolean {
        return with(uiState) {
            nombre.isNotBlank() && sabor.isNotBlank() && descripcion.isNotBlank() &&
                    precioBase.isNotBlank() && cantidad.isNotBlank()
        }
    }

    suspend fun saveItem() {
        if (validateInput()) {
            heladoRepository.insertHelado(heladoUiState.heladoDetails.toHelado())
        }
    }
}

data class HeladoUiState(
    val heladoDetails: HeladoDetails = HeladoDetails(),
    val isEntryValid: Boolean = false
)

data class HeladoDetails(
    val id: Int = 0,
    val nombre: String = "",
    val sabor: String = "",
    val descripcion: String = "",
    val precioBase: String = "",
    val cantidad: String = ""
)

fun HeladoDetails.toHelado(): Helado = Helado(
    id = id,
    nombre = nombre,
    sabor = sabor,
    descripcion = descripcion,
    precioBase = precioBase.toDoubleOrNull() ?: 0.0,
    cantidad = cantidad.toIntOrNull() ?: 0
)

fun Helado.formatedPrice(): String {
    return NumberFormat.getCurrencyInstance().format(precioBase)
}

fun Helado.formatedCantidad(): String {
    return cantidad.toString()
}

fun Helado.toHeladoUiState(isEntryValid: Boolean = false): HeladoUiState = HeladoUiState(
    heladoDetails = this.toHeladoDetails(),
    isEntryValid = isEntryValid
)

fun Helado.toHeladoDetails(): HeladoDetails = HeladoDetails(
    id = id,
    nombre = nombre,
    sabor = sabor,
    descripcion = descripcion,
    precioBase = precioBase.toString(),
    cantidad = cantidad.toString()
)
