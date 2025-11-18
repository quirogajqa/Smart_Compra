package com.example.smartcompra.data.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "productos",
    foreignKeys = [
        ForeignKey(
            entity = ListaCompra::class,
            parentColumns = ["listaId"],
            childColumns = ["listaPropietariaId"],
            onDelete = ForeignKey.CASCADE // Si se borra la lista, se borran sus productos
        )
    ]
)

data class Producto(
    @PrimaryKey(autoGenerate = true)
    val productoId: Long = 0,
    val nombre: String,
    val cantidad: Int,
    val precio: Double,
    val descuento: Int,
    val pack: Int,
    val precioFinal: Double,
    val fechaIngreso: Long = System.currentTimeMillis(),
    val listaPropietariaId: Long
)