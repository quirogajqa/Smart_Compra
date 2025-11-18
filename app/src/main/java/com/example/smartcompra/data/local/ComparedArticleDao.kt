package com.example.smartcompra.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.smartcompra.data.models.ArticuloComparado

@Dao
interface ComparedArticleDao {

    @Query("SELECT *  FROM Compared_article")
    suspend fun getAllComparedArticles(): List<ArticuloComparado>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticuloComparado>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticuloComparado)

    @Query("DELETE FROM Compared_article")
    suspend fun deleteAllArticles()

    @Query("DELETE FROM Compared_article WHERE id = :id")
    suspend fun deleteArticleById(id: String)
}