package com.uleam.appparahelados.ui.Clasico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import com.uleam.appparahelados.data.Helado.Helado
import com.uleam.appparahelados.data.Helado.interfaces.HeladoRepository

class ClasicoViewModel(private val heladoRepository: HeladoRepository) : ViewModel() {

    private val _cantidadHelados = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val cantidadHelados: StateFlow<Map<Int, Int>> = _cantidadHelados

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val heladoUiState: StateFlow<ClasicoUiState> =
        heladoRepository.getAllHeladosStream()
            .map { ClasicoUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = ClasicoUiState()
            )

    data class ClasicoUiState(val clasicoList: List<Helado> = listOf())

    fun incrementarCantidad(id: Int) {
        val current = _cantidadHelados.value.toMutableMap()
        current[id] = (current[id] ?: 0) + 1
        _cantidadHelados.value = current
    }

    fun decrementarCantidad(id: Int) {
        val current = _cantidadHelados.value.toMutableMap()
        val cantidadActual = current[id] ?: 0
        if (cantidadActual > 0) {
            current[id] = cantidadActual - 1
            _cantidadHelados.value = current
        }
    }
}
