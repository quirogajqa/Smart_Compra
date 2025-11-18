package com.example.smartcompra.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "Compared_article")
data class ArticuloComparado(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val marca: String?,
    val cantidad: Int,
    val precio: Double,
    val unidad: String,
    val descuento: Int,
    val pack: Int,
    val precioNormalizado: Double,
    val unidadNormalizada: String,
    val bestPrice: Boolean = false
)
