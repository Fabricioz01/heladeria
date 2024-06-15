

package com.uleam.appparahelados.ui.admin.topping.entry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uleam.appparahelados.HeladeriaTopAppBar
import com.uleam.appparahelados.R
import com.uleam.appparahelados.ui.AppViewModelProvider
import com.uleam.appparahelados.ui.navigation.NavigationController
import kotlinx.coroutines.launch
import java.util.Currency
import java.util.Locale

object ToppingEntryDestination : NavigationController {
    override val route = "item_entry"
    override val titleRes = R.string.topping_entry_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ToppingEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: ToppingEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            HeladeriaTopAppBar(
                title = stringResource(ToppingEntryDestination.titleRes),
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding ->
        ToppingEntryBody(
            toppingUiState = viewModel.toppingUiState,
            onItemValueChange = viewModel::updateUiState,
            onSaveClick = { coroutineScope.launch {
                viewModel.saveItem()
                navigateBack()
             } },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding()
                )
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun ToppingEntryBody(
    toppingUiState: ToppingUiState,
    onItemValueChange: (ToppingDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        ToppingInputForm(
            toppingDetails = toppingUiState.toppingDetails,
            onValueChange = onItemValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = toppingUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF8A80),
                contentColor = Color.White
            )
        ) {
            Text(text = stringResource(R.string.save_action))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToppingInputForm(
    toppingDetails: ToppingDetails,
    modifier: Modifier = Modifier,
    onValueChange: (ToppingDetails) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = toppingDetails.nombre,
            onValueChange = { onValueChange(toppingDetails.copy(nombre = it)) },
            label = { Text(stringResource(R.string.topping_name_req)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.Black,
                focusedBorderColor = Color(0xFFFF8A80),
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = toppingDetails.precio,
            onValueChange = { onValueChange(toppingDetails.copy(precio = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.topping_price_req)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.Black,
                focusedBorderColor = Color(0xFFFF8A80),
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = Color.Gray
            ),
            leadingIcon = { Text(Currency.getInstance(Locale.getDefault()).symbol) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = toppingDetails.cantidad,
            onValueChange = { onValueChange(toppingDetails.copy(cantidad = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text(stringResource(R.string.topping_cantidad_req)) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.Black,
                focusedBorderColor = Color(0xFFFF8A80),
                unfocusedBorderColor = Color.Gray,
                disabledBorderColor = Color.Gray
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        if (enabled) {
            Text(
                text = stringResource(R.string.required_fields),
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}
