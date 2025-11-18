package com.example.smartcompra.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smartcompra.data.models.Producto

@Dao
interface CurrentListpurchasedDao {

    @Query("SELECT *  FROM productos")
    suspend fun getAllPurchasedArticles(): List<Producto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<Producto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Producto)

    @Query("DELETE FROM productos")
    suspend fun deleteAllArticles()

    @Query("DELETE FROM productos WHERE id = :id")
    suspend fun deleteArticleById(id: String)
}