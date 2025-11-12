package com.example.smartcompra.data.models

data class Producto(
    val nombre: String,
    val marca: String?,
    val cantidad: Int,
    val precio: Int,
    val unidad: String,
    val descuento: Int,
    val pack: Int,
    val precioNormalizado: Int,
    val unidadNormalizada: String,
    val bestPrice: Boolean = false
)
