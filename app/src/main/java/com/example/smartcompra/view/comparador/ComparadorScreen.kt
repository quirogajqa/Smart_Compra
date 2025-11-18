@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.smartcompra.view.comparador

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcompra.view.comparador.components.AgregarProductoScreen
import com.example.smartcompra.view.comparador.components.ProductoCard
import com.example.smartcompra.viewmodel.ComparadorViewModel
import kotlinx.coroutines.launch

@Composable
fun ComparadorScreen(
    viewModel: ComparadorViewModel = hiltViewModel()
) {

    val uiState by viewModel.productoUiState.collectAsStateWithLifecycle()
    val productList by viewModel.productList.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Box(
        Modifier
            .fillMaxSize()
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
            }
        } else {

            // Contenido principal
            if (productList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Ingrese un producto para comparar precios",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = { viewModel.onDeleteAll() }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CleaningServices,
                                    contentDescription = "Limpiar",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                    items(
                        items = productList,
                    ) { producto ->
                        ProductoCard(articuloComparado = producto)
                    }
                }
            }
        }

        SmallFloatingActionButton(
            onClick = { viewModel.onShowDialog(true) },
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar un producto",
                tint = MaterialTheme.colorScheme.onTertiary
            )
        }
    }

    if (uiState.showDialog) {
        val closeSheetAction: () -> Unit = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    viewModel.onShowDialog(false)
                }
            }
        }

        ModalBottomSheet(
            onDismissRequest = closeSheetAction,
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
                AgregarProductoScreen(
                    onCloseSheet = closeSheetAction
                )
            }
        }
    }
}