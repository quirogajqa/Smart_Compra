package com.example.smartcompra.view.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smartcompra.view.comparador.ComparadorScreen
import com.example.smartcompra.view.compras.ComprasScreen
import com.example.smartcompra.view.home.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {

        composable(Home.route) {
            HomeScreen()
        }

        composable(Comparador.route) {
            ComparadorScreen()
        }

        composable(Carrito.route) {
            ComprasScreen()
        }
    }
}