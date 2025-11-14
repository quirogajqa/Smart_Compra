package com.example.smartcompra.view.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.smartcompra.view.navigation.AppNavigation


@Composable
fun HomeScreen(
    navigateToComparador: @Composable () -> Unit,
    navigateToCarrito: @Composable () -> Unit
) {
    // 💡 Aquí se define el estado de navegación actual
    //val navBackStackEntry by navController.currentBackStackEntryAsState()
    //val currentDestination = navBackStackEntry?.destination

    Scaffold(
        // Remueve el BottomAppBar del total de ComprasScreen/ComparadorScreen
        // e implementa la NavigationBar aquí:
        bottomBar = {
            NavigationBar(
                // Fondo de la NavigationBar (usamos Surface o Secondary para un look más moderno)
                containerColor = MaterialTheme.colorScheme.surface,
            ) {
                // Item 1: Comparador
                NavigationBarItem(
                    selected = true, //currentDestination?.route == "comparador",
                    onClick = { navigateToComparador },
                    icon = {
                        Icon(
                            Icons.Filled.Scale, // Asumiendo que tienes un Scale/Gráfico
                            contentDescription = "Comparador"
                        )
                    },
                    label = { Text("Comparar") },
                    colors = NavigationBarItemDefaults.colors(
                        // 🟢 Color del icono/texto seleccionado: Verde Esmeralda (primary)
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        // ⚪ Color del icono/texto no seleccionado: Negro/Blanco (onSurface)
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )

                // Item 2: Lista de Compras
                NavigationBarItem(
                    selected = true, //currentDestination?.route == "compras",
                    onClick = { navigateToCarrito },
                    icon = {
                        Icon(
                            Icons.Filled.ShoppingCart, // Asumiendo que tienes un ShoppingCart/List
                            contentDescription = "Compras"
                        )
                    },
                    label = { Text("Lista") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                )
            }
        }
    ) { padding ->
        // Aquí se muestra el NavHost con ComparadorScreen o ComprasScreen
        Column(
            Modifier
                .padding(padding)
            //.padding(10.dp)
        ) {
        }
    }
}