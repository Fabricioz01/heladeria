package com.uleam.appparahelados.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uleam.appparahelados.R
import com.uleam.appparahelados.ui.AppViewModelProvider
import com.uleam.appparahelados.ui.navigation.NavigationController
import com.uleam.appparahelados.ui.theme.md_theme_light_onSecondary

object AdminDestionation : NavigationController {
    override val route = "admin"
    override val titleRes = R.string.admin_title
}

@Composable
fun AdminScreen(
    navigateTopping: () -> Unit,
    navigateHelado: () -> Unit,
    navigateCerrarSesion: () -> Unit,
    viewModel: AdminViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = navigateCerrarSesion
                        ) {
                            Icon(
                                Icons.Filled.Logout,
                                contentDescription = "Cerrar Sesión",
                                tint = Color.Black
                            )
                        }

                        Text(
                            text = "Cerrar Sesión",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        },
    ) { paddingValues ->
        MainContent(
            navigateTopping = { navigateTopping() },
            navigateHelado = { navigateHelado() },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun Encabezado() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Administración de Helados",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun MainContent(
    navigateTopping: () -> Unit,
    navigateHelado: () -> Unit,
    modifier: Modifier = Modifier
) {
    var menuVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Encabezado()

        Spacer(modifier = Modifier.height(40.dp))

        IconButton(
            onClick = { menuVisible = !menuVisible },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(Icons.Filled.Settings, contentDescription = "Menu")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (menuVisible) {
            MenuContent(
                navigateTopping = navigateTopping,
                navigateHelado = navigateHelado,
                onCloseMenu = { menuVisible = false }
            )
        }
    }
}

@Composable
fun MenuContent(
    navigateTopping: () -> Unit,
    navigateHelado: () -> Unit,
    onCloseMenu: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                navigateTopping()
                onCloseMenu()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = md_theme_light_onSecondary
            )
        ) {
            Text(
                "Administrar Toppings",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navigateHelado()
                onCloseMenu()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = md_theme_light_onSecondary
            )
        ) {
            Text(
                "Administrar Sabores",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
