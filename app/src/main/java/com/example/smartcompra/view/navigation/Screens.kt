package com.example.smartcompra.view.navigation

import kotlinx.serialization.Serializable
sealed class Destination(val route: String)

object Home: Destination("home_route")
object Comparador : Destination("comparador_route")
object Carrito : Destination("carrito_route")

@Serializable
data class DetalleLista (
    val nombre: String,
    val listaId: Long,
    val total: Double
)