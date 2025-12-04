package com.example.smartcompra.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.smartcompra.data.models.ArticuloToSave
import com.example.smartcompra.data.models.ListaCompra
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Insert
    suspend fun insertLista(lista: ListaCompra): Long

    @Insert
    suspend fun insertProductos(productos: List<ArticuloToSave>)

    @Transaction
    @Query("SELECT * FROM listas_compras WHERE listaId = :id")
    suspend fun getListaConProductos(id: Long): ListaConProductos

    @Query("SELECT * FROM listas_compras")
    suspend fun getAllListas(): List<ListaCompra>

    @Delete
    suspend fun deleteLista(lista: ListaCompra)

}