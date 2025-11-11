package com.example.smartcompra.data.models

data class Producto(
    val nombre: String = "",
    val cantidad: Int = 0,
    val precio: Int = 0,
    val unidad: String = "",
    val precioNormalizado: Int = 0
)
