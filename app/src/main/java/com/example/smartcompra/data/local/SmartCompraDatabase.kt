package com.example.smartcompra.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smartcompra.data.models.ArticuloComparado
import com.example.smartcompra.data.models.ArticuloComprado
import com.example.smartcompra.data.models.ListaCompra
import com.example.smartcompra.data.models.Producto


@Database(
    entities = [ArticuloComprado::class, ArticuloComparado::class, ListaCompra::class, Producto::class],
    version = 1,
    exportSchema = false
)
abstract class SmartCompraDatabase: RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao

    abstract fun comparedArticleDao(): ComparedArticleDao

    abstract fun currentListpurchasedDao(): CurrentListpurchasedDao

    companion object{
        @Volatile
        private var INSTANCE: SmartCompraDatabase? = null

        fun getDatabase(context: Context): SmartCompraDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SmartCompraDatabase::class.java,
                    "smartCompra_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}