package com.uleam.appparahelados.ui.admin.helado

import HeladoViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uleam.appparahelados.HeladeriaTopAppBar
import com.uleam.appparahelados.R
import com.uleam.appparahelados.data.Helado.Helado
import com.uleam.appparahelados.ui.AppViewModelProvider
import com.uleam.appparahelados.ui.admin.helado.entry.formatedPrice
import com.uleam.appparahelados.ui.navigation.NavigationController

object HeladoDestination : NavigationController {
    override val route = "helado"
    override val titleRes = R.string.helado
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeladoScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    navigateAdmin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HeladoViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val heladoUiState by viewModel.heladoUiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HeladeriaTopAppBar(
                title = stringResource(HeladoDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                containerColor = Color.Red,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.helado_entry_title),
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            FloatingActionButton(
                onClick = { navigateAdmin() },
                shape = MaterialTheme.shapes.medium,
                containerColor = Color.Gray,
                contentColor = Color.White,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.regresar),
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            HeladoBody(
                heladoList = heladoUiState.heladoList,
                onItemClick = navigateToItemUpdate,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 16.dp)
            )
        }
    }
}

@Composable
private fun HeladoBody(
    heladoList: List<Helado>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (heladoList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_helados),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            HeladosList(
                heladoList = heladoList,
                onItemClick = { onItemClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun HeladosList(
    heladoList: List<Helado>,
    onItemClick: (Helado) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = heladoList, key = { it.id }) { helado ->
            InventoryHelado(
                helado = helado,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(helado) }
            )
        }
    }
}

@Composable
private fun InventoryHelado(
    helado: Helado,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFF0C2)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = helado.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF6D4C41)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = helado.formatedPrice(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF6D4C41)
                )
            }
            Text(
                text = stringResource(R.string.in_stock, helado.cantidad),
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF6D4C41)
            )
        }
    }
}
