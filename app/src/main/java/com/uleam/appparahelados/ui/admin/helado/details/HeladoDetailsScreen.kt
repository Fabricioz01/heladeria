package com.uleam.appparahelados.ui.admin.helado.details


import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uleam.appparahelados.HeladeriaTopAppBar
import com.uleam.appparahelados.R
import com.uleam.appparahelados.data.Helado.Helado
import com.uleam.appparahelados.ui.AppViewModelProvider
import com.uleam.appparahelados.ui.admin.helado.entry.toHelado
import com.uleam.appparahelados.ui.navigation.NavigationController
import kotlinx.coroutines.launch

object HeladoDetailsDestination : NavigationController {
    override val route = "helado_details"
    override val titleRes = R.string.helado_detail_title
    const val heladoIdArg = "heladoId"
    val routeWithArgs = "$route/{$heladoIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeladoDetailsScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HeladoDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            HeladeriaTopAppBar(
                title = stringResource(HeladoDetailsDestination.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        HeladoDetailsBody(
            heladoDetailsUiState = uiState,
            heladoEdit = { navigateToEditItem(uiState.heladoDetails.id) },
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteItem()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun HeladoDetailsBody(
    heladoDetailsUiState: HeladoDetailsUiState,
    heladoEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        HeladoDetails(
            helado = heladoDetailsUiState.heladoDetails.toHelado(), modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = heladoEdit,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            enabled = !heladoDetailsUiState.outOfStock,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF8A80),
                contentColor = Color.White
            )
        ) {
            Text(stringResource(R.string.helado_editor))
        }
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFFEF6C00),
            )
        ) {
            Text(stringResource(R.string.eliminar))
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun HeladoDetails(
    helado: Helado,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF0C2),
            contentColor = Color(0xFF6D4C41)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HeladoDetailsRow(
                labelResID = R.string.helado_nombre,
                itemDetail = helado.nombre,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
            HeladoDetailsRow(
                labelResID = R.string.helado_cantidad,
                itemDetail = helado.cantidad.toString(),
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
            HeladoDetailsRow(
                labelResID = R.string.helado_precioBase,
                itemDetail = helado.precioBase.toString(),
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            )
        }
    }
}

@Composable
private fun HeladoDetailsRow(
    @StringRes labelResID: Int, itemDetail: String, modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = stringResource(labelResID))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = itemDetail, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {  },
        title = { Text(stringResource(R.string.attention), color = Color(0xFFEF6C00)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(stringResource(R.string.no), color = Color(0xFFEF6C00))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(stringResource(R.string.yes), color = Color(0xFFEF6C00))
            }
        }
    )
}
