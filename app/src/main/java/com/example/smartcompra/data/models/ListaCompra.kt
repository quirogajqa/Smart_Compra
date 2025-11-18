package com.example.smartcompra.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "listas_compras")
data class ListaCompra(
    @PrimaryKey(autoGenerate = true)
    val listaId: Long = 0,
    val nombre: String,
    val total: Double
)