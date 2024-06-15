
package com.uleam.appparahelados.ui.admin.topping

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
import com.uleam.appparahelados.data.Topping.Topping
import com.uleam.appparahelados.ui.AppViewModelProvider
import com.uleam.appparahelados.ui.admin.topping.entry.formatedPrice
import com.uleam.appparahelados.ui.navigation.NavigationController

object ToppingDestination : NavigationController {
    override val route = "topping"
    override val titleRes = R.string.topping
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToppingScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    navigateAdmin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ToppingViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val toppingUiState by viewModel.toppingUiState.collectAsState()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HeladeriaTopAppBar(
                title = stringResource(ToppingDestination.titleRes),
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
                    contentDescription = stringResource(R.string.topping_entry_title),
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
            ToppingBody(
                toppingList = toppingUiState.toppingList,
                onItemClick = navigateToItemUpdate,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 16.dp)
            )
        }
    }
}

    @Composable
private fun ToppingBody(
    toppingList: List<Topping>,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (toppingList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_toppings),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            ToppingsList(
                toppingList = toppingList,
                onItemClick = { onItemClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}

@Composable
private fun ToppingsList(
    toppingList: List<Topping>,
    onItemClick: (Topping) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = toppingList, key = { it.id }) { topping ->
            InventoryTopping(
                topping = topping,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(topping) }
            )
        }
    }
}

@Composable
private fun InventoryTopping(
    topping: Topping,
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
                    text = topping.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF6D4C41)
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = topping.formatedPrice(),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF6D4C41)
                )
            }
            Text(
                text = stringResource(R.string.in_stock, topping.cantidad),
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF6D4C41)
            )
        }
    }
}

