package com.example.smartcompra.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.smartcompra.data.models.ListaCompra
import com.example.smartcompra.data.models.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface CompraDao {

    // 1. Guardar la ListaCompra y obtener su ID.
    @Insert
    suspend fun insertLista(lista: ListaCompra): Long

    // 2. Guardar los productos (Nota: la clave foránea debe estar establecida en cada Producto).
    @Insert
    suspend fun insertProductos(productos: List<Producto>)

    // 3. Consultar la relación completa (Lista + Productos)
    @Transaction
    @Query("SELECT * FROM listas_compras WHERE listaId = :id")
    fun getListaConProductos(id: Long): Flow<ListaConProductos>

    // 4. Obtener todas las listas
    @Query("SELECT * FROM listas_compras")
    fun getAllListas(): Flow<List<ListaCompra>>
}