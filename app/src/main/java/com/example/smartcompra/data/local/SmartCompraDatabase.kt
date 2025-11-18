package com.example.smartcompra.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smartcompra.data.models.ArticuloComprado


@Database(
    entities = [ArticuloComprado::class],
    version = 1,
    exportSchema = false
)
abstract class SmartCompraDatabase: RoomDatabase() {
    abstract fun articuloCompradoDao(): ArticuloCompradoDao

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