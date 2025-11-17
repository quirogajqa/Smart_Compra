package com.example.smartcompra.view.navigation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.example.smartcompra.view.navigation.Carrito
import com.example.smartcompra.view.navigation.Comparador
import com.example.smartcompra.view.navigation.Home

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentTopBar(selectedDestination: String) {

    when (selectedDestination) {
        Comparador.route -> {
            TopAppBar(
                title = { Text("Comparador de precios") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        Home.route -> {
            TopAppBar(
                title = { Text("Home") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

        Carrito.route -> {
            TopAppBar(
                title = { Text("Mi lista de compras") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}
