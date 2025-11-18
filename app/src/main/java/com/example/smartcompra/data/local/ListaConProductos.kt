package com.example.smartcompra.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.example.smartcompra.data.models.ArticuloComprado
import com.example.smartcompra.data.models.ListaCompra

data class ListaConProductos(
    @Embedded
    val lista: ListaCompra,

    @Relation(
        parentColumn = "listaId",
        entityColumn = "listaPropietariaId"
    )
    val productos: List<ArticuloComprado>
)