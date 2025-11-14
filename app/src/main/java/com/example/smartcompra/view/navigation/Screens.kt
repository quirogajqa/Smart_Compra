package com.example.smartcompra.view.navigation

sealed class Destination(val route: String)

object Home: Destination("home_route")
object Comparador : Destination("comparador_route")
object Carrito : Destination("carrito_route")