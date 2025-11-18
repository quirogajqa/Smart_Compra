package com.example.smartcompra.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcompra.data.models.ListaCompra
import com.example.smartcompra.view.comparador.components.AgregarProductoScreen
import com.example.smartcompra.view.comparador.components.ProductoCard
import com.example.smartcompra.view.home.components.ListaCompraCard
import com.example.smartcompra.viewmodel.ComparadorViewModel
import com.example.smartcompra.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetails: (ListaCompra) -> Unit,
) {
    val uiState by viewModel.UiState.collectAsStateWithLifecycle()
    val productList by viewModel.comprasList.collectAsStateWithLifecycle()

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
                        text = "No hay listas de compras guardadas",
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
                    items(
                        items = productList,
                    ) { lista ->
                        ListaCompraCard(
                            listaCompra = lista,
                            onClick = { navigateToDetails(lista) }
                        )
                    }
                }
            }
        }
    }
}