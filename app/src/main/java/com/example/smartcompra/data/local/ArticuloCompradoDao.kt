package com.example.smartcompra.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smartcompra.data.models.ArticuloComprado

@Dao
interface ArticuloCompradoDao {

    @Query("SELECT *  FROM articulos_comprados")
    suspend fun getPurchasedArticles(): List<ArticuloComprado>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticuloComprado>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticuloComprado)

    @Query("DELETE FROM articulos_comprados")
    suspend fun deleteAllArticles()

    @Query("DELETE FROM articulos_comprados WHERE id = :id")
    suspend fun deleteArticleById(id: String)
}