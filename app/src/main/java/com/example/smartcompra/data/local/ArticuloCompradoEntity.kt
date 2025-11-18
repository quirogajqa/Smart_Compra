package com.example.smartcompra.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smartcompra.data.models.ArticuloComprado
import java.util.UUID

@Entity(tableName = "news")
data class ArticuloCompradoEntity(
    @PrimaryKey val numbreId: String = UUID.randomUUID().toString(),
    val nombre: String,
    val cantidad: Int,
    val precio: Double,
    val descuento: Int,
    val pack: Int,
    val precioFinal: Double,
    val fechaIngreso: Long
)
