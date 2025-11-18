package com.example.smartcompra.view.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcompra.utils.toChileanPesos
import com.example.smartcompra.view.compras.components.OrdenamientoDropdown
import com.example.smartcompra.view.detail.components.ArticuloCard
import com.example.smartcompra.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    nombre: String,
    listaId: Long,
    total: Double,
    onNavigateBack: () -> Unit
) {
    viewModel.loadListaConProductos(listaId)
    val uiState by viewModel.UiState.collectAsStateWithLifecycle()
    val productList by viewModel.productos.collectAsStateWithLifecycle()
    val criterioActual by viewModel.criterioOrdenamiento.collectAsStateWithLifecycle()


    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text("Lista: $nombre")

                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver atrás",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    OrdenamientoDropdown(
                        criterioActual = criterioActual,
                        onCriterioSeleccionado = { nuevoCriterio ->
                            viewModel.setCriterioOrdenamiento(nuevoCriterio)
                        },
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.tertiary)
                }
            } else {
                Box(
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = productList,
                        ) { articulo ->
                            ArticuloCard(articuloComprado = articulo)
                        }
                        item {
                            Spacer(Modifier.height(36.dp))
                        }

                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 16.dp, end = 16.dp, bottom = 2.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        "Total: ", fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        total.toChileanPesos(),
                        textAlign = TextAlign.Right,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}
