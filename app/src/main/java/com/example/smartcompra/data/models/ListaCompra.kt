package com.example.smartcompra.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "listas_compras")
data class ListaCompra(
    @PrimaryKey(autoGenerate = true)
    val listaId: Long = 0,
    val nombre: String,
    val total: Double,
    val numeroArticulos: Int,
    val fechaActual: String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
)