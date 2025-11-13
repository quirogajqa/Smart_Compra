package com.example.smartcompra.view.compras

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcompra.utils.toChileanPesos
import com.example.smartcompra.view.comparador.components.ProductoCard
import com.example.smartcompra.view.compras.components.AgregarCompraScreen
import com.example.smartcompra.view.compras.components.CompraCard
import com.example.smartcompra.viewmodel.ComprasViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComprasScreen(
    viewModel: ComprasViewModel = hiltViewModel()
) {

    val uiState by viewModel.comprasUiState.collectAsStateWithLifecycle()
    val compraList by viewModel.comprasList.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de compras") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = { viewModel.onShowDialog(true) },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.tertiary,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar un producto",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {
                        Text(
                            "Total: ", fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Spacer(Modifier.width(12.dp))

                    Column(
                    ) {
                        Text(
                            uiState.total.toChileanPesos(),
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                }
            }

        }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
            //.padding(10.dp)
        ) {

            // Contenido principal
            if (compraList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ingrese un producto para comparar precios",
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = compraList,
                    ) { compra ->
                        CompraCard(compra = compra)
                    }
                }
            }
        }
    }

    if (uiState.showDialog) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.onShowDialog(false) },
            Modifier
                .padding(8.dp),
            sheetState = sheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 2.dp,
        ) {
            Column(
                Modifier
                    .verticalScroll(scrollState)
            ) {
                AgregarCompraScreen()
            }
        }

    }
}