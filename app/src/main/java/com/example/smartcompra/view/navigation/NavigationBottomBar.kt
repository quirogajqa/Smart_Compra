package com.example.smartcompra.view.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smartcompra.view.navigation.components.CurrentTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationBottomBar() {
    val navController = rememberNavController()
    val startDestination: Destination = Home
    var selectedDestination by rememberSaveable {
        mutableStateOf(startDestination.route)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val routesWithBottomBar = listOf(Home.route, Comparador.route, Carrito.route)
    val shouldShowBottomBar = currentRoute in routesWithBottomBar

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            if (shouldShowBottomBar) {
                CurrentTopBar(selectedDestination)
            }
        },
        bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar(
                    windowInsets = NavigationBarDefaults.windowInsets,
                    containerColor = MaterialTheme.colorScheme.surface,
                ) {
                    NavigationBarItem(
                        selected = selectedDestination == Comparador.route,
                        onClick = {
                            navController.navigate(route = Comparador.route)
                            selectedDestination = Comparador.route
                        },
                        icon = { Icon(Icons.Filled.SwapVert, contentDescription = "Comparador") },
                        label = { Text("Comparar") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    NavigationBarItem(
                        selected = selectedDestination == Home.route,
                        onClick = {
                            navController.navigate(route = Home.route)
                            selectedDestination = Home.route
                        },
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Comparador") },
                        label = { Text("Home") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    NavigationBarItem(
                        selected = selectedDestination == Carrito.route,
                        onClick = {
                            navController.navigate(route = Carrito.route)
                            selectedDestination = Carrito.route
                        },
                        icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Compras") },
                        label = { Text("Compra") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    ) { contentPadding ->
        AppNavigation(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier
                .padding(contentPadding)
        )
    }
}
