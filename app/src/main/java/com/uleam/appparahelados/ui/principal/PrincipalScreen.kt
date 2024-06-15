package com.uleam.appparahelados.ui.principal

import PrincipalViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uleam.appparahelados.R
import com.uleam.appparahelados.ui.AppViewModelProvider
import com.uleam.appparahelados.ui.navigation.NavigationController
import com.uleam.appparahelados.ui.theme.md_theme_light_onSecondary

object PrincipalDestionation : NavigationController {
    override val route = "principal"
    override val titleRes = R.string.registros_title
    const val IdArg = "UserId"
    val routeWithArgs = "${PrincipalDestionation.route}/{$IdArg}"
}

@Composable
fun PrincipalScreen(
    navigateToHelados: () -> Unit,
    navigateToCloseSession: () -> Unit,
    viewModel: PrincipalViewModel = viewModel(factory = AppViewModelProvider.Factory)
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
                            onClick = navigateToCloseSession
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
        content = { paddingValues ->
            MainContent(modifier = Modifier.padding(paddingValues), navigateToHelados)
        }
    )
}


@Composable
fun Encabezado() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Heladería Sammy",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.portada),
            contentDescription = "Logo de Heladería Sammy",
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, navigateToHelados: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Encabezado()
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navigateToHelados() },
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = md_theme_light_onSecondary
            ),
            shape = MaterialTheme.shapes.large
        ) {
            Text(
                "Helado Clásico",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}
