package com.uleam.appparahelados.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.uleam.appparahelados.R
import com.uleam.appparahelados.ui.AppViewModelProvider
import com.uleam.appparahelados.ui.navigation.NavigationController
import com.uleam.appparahelados.ui.theme.md_theme_light_onSecondary
import com.uleam.appparahelados.ui.theme.md_theme_light_onSurfaceVariant
import com.uleam.appparahelados.ui.theme.md_theme_light_secondary
import kotlinx.coroutines.launch

object LoginDestinationScreen : NavigationController {
    override val route = "login"
    override val titleRes = R.string.login_title
}
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navigateTohome: () -> Unit,
    navigateToRegister: () -> Unit,
    navigateToAdmin: () -> Unit,
    viewModel: LoginViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    var correo by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    val alertDialogVisibleState = remember { mutableStateOf(false) }
    val showErrorDialog = remember { mutableStateOf(false) }
    val dialogRole = remember { mutableStateOf<String?>(null) }

    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Encabezado()
            Spacer(modifier = Modifier.height(16.dp))

            val textFieldModifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .padding(vertical = 4.dp, horizontal = 16.dp)

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text(text = "Correo electrónico") },
                modifier = textFieldModifier,
                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = md_theme_light_onSurfaceVariant),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                singleLine = true
            )

            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text(text = "Contraseña") },
                modifier = textFieldModifier,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisibility)
                        painterResource(id = R.drawable.visibility)
                    else painterResource(id = R.drawable.visible)

                    IconButton(onClick = {
                        passwordVisibility = !passwordVisibility
                    }) {
                        Icon(painter = image, contentDescription = if (passwordVisibility) "Ocultar contraseña" else "Mostrar contraseña")
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = md_theme_light_onSurfaceVariant),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                singleLine = true

            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.viewModelScope.launch {
                        val role = viewModel.validateUser(correo, pass)
                        if (role != null) {
                            dialogRole.value = role
                            alertDialogVisibleState.value = true
                        } else {
                            showErrorDialog.value = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = md_theme_light_onSecondary
                )
            ) {
                Text(text = "Iniciar Sesión")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "¿No tienes cuenta? ",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = "Regístrate",
                    color = Color.Blue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable { navigateToRegister() }
                )
            }

            if (showErrorDialog.value) {
                AlertDialog(
                    onDismissRequest = { showErrorDialog.value = false },
                    title = { Text("Error") },
                    text = { Text("Credenciales inválidas. Por favor, inténtalo de nuevo.") },
                    confirmButton = {
                        Button(
                            onClick = { showErrorDialog.value = false },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = md_theme_light_secondary,
                                contentColor = md_theme_light_onSecondary
                            )
                        ) {
                            Text("OK")
                        }
                    }
                )
            }
        }
    }

    if (alertDialogVisibleState.value) {
        InicioSesionExitosoDialog(
            onClose = {
                alertDialogVisibleState.value = false
                when (dialogRole.value) {
                    "Admin" -> navigateToAdmin()
                    "Usuario" -> navigateTohome()
                }
            },
            role = dialogRole.value
        )
    }
}


@Composable
fun InicioSesionExitosoDialog(onClose: () -> Unit, role: String?) {
    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("¡Inicio de Sesión Exitoso!") },
        text = { Text("¡Has iniciado sesión exitosamente como $role! ¿Deseas continuar?") },
        confirmButton = {
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(
                    containerColor = md_theme_light_secondary,
                    contentColor = md_theme_light_onSecondary
                )
            ) {
                Text("OK")
            }
        }
    )
}
@Composable
fun Encabezado() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp)) // Espacio adicional arriba del texto
        Text(
            text = "Heladería Sammy",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )

        Image(
            painter = painterResource(id = R.drawable.portada),
            contentDescription = "Logo de Heladería Sammy jj",
            modifier = Modifier
                .size(200.dp)
        )
    }
}