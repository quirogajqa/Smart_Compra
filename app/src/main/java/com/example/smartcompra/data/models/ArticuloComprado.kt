package com.example.smartcompra.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "articulos_comprados")
data class ArticuloComprado(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val cantidad: Int,
    val precio: Double,
    val descuento: Int,
    val pack: Int,
    val precioFinal: Double,
    val fechaIngreso: Long = System.currentTimeMillis()
)