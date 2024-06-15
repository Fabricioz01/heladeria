import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uleam.appparahelados.data.Helado.Helado
import com.uleam.appparahelados.data.Helado.interfaces.HeladoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HeladoViewModel(heladoRepository: HeladoRepository): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
    val heladoUiState: StateFlow<HeladoUiState> =
        heladoRepository.getAllHeladosStream().map { HeladoUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = HeladoUiState()
            )
}

data class HeladoUiState(val heladoList: List<Helado> = listOf())
