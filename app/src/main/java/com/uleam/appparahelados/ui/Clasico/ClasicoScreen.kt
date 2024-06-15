import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.uleam.appparahelados.R
import com.uleam.appparahelados.ui.AppViewModelProvider
import com.uleam.appparahelados.ui.Clasico.ClasicoViewModel
import com.uleam.appparahelados.ui.navigation.NavigationController
import kotlinx.coroutines.delay

object HeladoClasicoDestionation : NavigationController {
    override val route = "helado_clasico"
    override val titleRes = R.string.helado_clasico
}

@Composable
fun ClasicoScreen(
    navController: NavHostController,
    viewModel: ClasicoViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val cantidades by viewModel.cantidadHelados.collectAsState()
    val sabores by viewModel.heladoUiState.collectAsState()
    var message by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        if (sabores.clasicoList.isEmpty()) {
            Text(
                text = "No se encuentran disponibles actualmente ningún helado.",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            sabores.clasicoList.forEach { helado ->
                val cantidad = cantidades[helado.id] ?: 0
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = helado.sabor,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = helado.descripcion,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = "Precio: $${helado.precioBase}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Cantidad: ${helado.cantidad}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            message = "¡Gracias! :)"
                            viewModel.incrementarCantidad(helado.id)
                        }) {
                            Icon(Icons.Default.ThumbUp, contentDescription = "Me gusta", tint = Color.Black)
                        }
                        IconButton(onClick = {
                            message = "¡Mejoraremos!"
                            viewModel.decrementarCantidad(helado.id)
                        }) {
                            Icon(Icons.Default.ThumbDown, contentDescription = "No me gusta", tint = Color.Black)
                        }
                    }
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }

        if (message != null) {
            LaunchedEffect(message) {
                delay(2000) // Mostrar el mensaje por 2 segundos
                message = null // Ocultar el mensaje después de 2 segundos
            }
            Text(
                text = message!!,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { navController.navigate("principal") },
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("Regresar al inicio", color = Color.White)
            }
            Button(
                onClick = { navController.navigate("user_topping") },
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("Toppings", color = Color.White)
            }
        }
    }
}
