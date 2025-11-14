package com.example.smartcompra.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smartcompra.view.home.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home) {
        composable<Home> {
            HomeScreen(
                navigateToComparador = { navController.navigate(Comparador) },
                navigateToCarrito = { navController.navigate(Carrito) }
            )
        }
    }
}