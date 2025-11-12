@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.smartcompra.view.comparador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcompra.view.comparador.components.AgregarProductoScreen
import com.example.smartcompra.view.comparador.components.ProductoCard
import com.example.smartcompra.viewmodel.ComparadorViewModel

@Composable
fun ComparadorScreen(
    viewModel: ComparadorViewModel = hiltViewModel()
) {

    val uiState by viewModel.productoUiState.collectAsStateWithLifecycle()
    val productList by viewModel.productList.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Comparador de precios") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.tertiary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onShowDialog(true) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar un producto"
                )
            }
        }
    ) { padding ->

        Column(
            Modifier
                .padding(padding)
                .padding(10.dp)
        ) {

            // Contenido principal
            if (productList.isEmpty()) {
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
                        items = productList,
                    ) { producto ->
                        ProductoCard(producto = producto)
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
            Column (
                Modifier
                    .verticalScroll(scrollState)
            ){
                AgregarProductoScreen()
            }
        }

    }
}