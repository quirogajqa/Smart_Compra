package com.example.smartcompra.di

import android.content.Context
import com.example.smartcompra.data.local.ArticuloCompradoDao
import com.example.smartcompra.data.local.SmartCompraDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSmartCompraDatabase(
        @ApplicationContext context: Context
    ): SmartCompraDatabase {
        return SmartCompraDatabase.getDatabase(context)
    }

    @Provides
    fun provideArticuloCompradoDao(database: SmartCompraDatabase): ArticuloCompradoDao {
        return database.articuloCompradoDao()
    }
}