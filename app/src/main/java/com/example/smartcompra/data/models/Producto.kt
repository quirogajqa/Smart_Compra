package com.example.smartcompra.data.models

data class Producto(
    val nombre: String,
    val cantidad: Int,
    val precio: Int,
    val unidad: String,
    val precioNormalizado: Int,
    val bestPrice: Boolean = false
)
