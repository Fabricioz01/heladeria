package com.uleam.appparahelados.ui.Clasico.toppings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.uleam.appparahelados.data.Topping.Topping
import com.uleam.appparahelados.ui.AppViewModelProvider
import com.uleam.appparahelados.ui.Clasico.topppings.UserToppingViewModel
import com.uleam.appparahelados.ui.navigation.NavigationController
import kotlinx.coroutines.delay

object UserToppingDestionation : NavigationController {
    override val route = "user_topping"
    override val titleRes = R.string.admin_title
}

@Composable
fun UserToppingScreen(
    navController: NavHostController,
    viewModel: UserToppingViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val cantidades by viewModel.cantidadToppings.collectAsState()
    val toppings by viewModel.toppingUiState.collectAsState()
    var message by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        if (toppings.toppingList.isEmpty()) {
            Text(
                text = "No se encuentran disponibles actualmente ningun topping.",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            toppings.toppingList.forEach { topping ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = topping.nombre,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Text(
                            text = "Precio: $${topping.precio}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Cantidad: ${topping.cantidad}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = {
                            message = "Gracias :)"
                            viewModel.incrementarCantidad(topping.id)
                        }) {
                            Icon(Icons.Default.ThumbUp, contentDescription = "Me gusta")
                        }
                        IconButton(onClick = {
                            message = "Mejoraremos"
                            viewModel.decrementarCantidad(topping.id)
                        }) {
                            Icon(Icons.Default.ThumbDown, contentDescription = "No me gusta")
                        }
                    }
                }
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }

        if (message != null) {
            LaunchedEffect(message) {
                delay(2000)
                message = null
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
            Button(onClick = { navController.navigate("principal") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                contentPadding = PaddingValues(16.dp)

            ) {
                Text("Regresar al inicio")

            }
            Button(onClick = { navController.navigate("helado_clasico") },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                contentPadding = PaddingValues(16.dp)

                ) {
                Text("Regresar a helados")

            }
        }
    }
}
