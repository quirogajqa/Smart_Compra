package com.example.smartcompra.data.models

data class Compra(
    val nombre: String,
    val cantidad: Int,
    val precio: Int,
    val descuento: Int,
    val pack: Int
)