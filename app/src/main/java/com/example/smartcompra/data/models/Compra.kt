package com.example.smartcompra.data.models

data class Compra(
    val nombre: String,
    val cantidad: Int,
    val precio: Double,
    val descuento: Int,
    val pack: Int,
    val precioFinal: Double,
    val fechaIngreso: Long = System.currentTimeMillis()
)