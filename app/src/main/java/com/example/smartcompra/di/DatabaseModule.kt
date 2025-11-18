package com.example.smartcompra.di

import android.content.Context
import com.example.smartcompra.data.local.ComparedArticleDao
import com.example.smartcompra.data.local.CurrentListpurchasedDao
import com.example.smartcompra.data.local.ShoppingListDao
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
    fun provideArticuloCompradoDao(database: SmartCompraDatabase): ShoppingListDao {
        return database.shoppingListDao()
    }

    @Provides
    fun provideComparedArticleDao(database: SmartCompraDatabase): ComparedArticleDao {
        return database.comparedArticleDao()
    }

    @Provides
    fun provideCurrentListpurchasedDao(database: SmartCompraDatabase): CurrentListpurchasedDao {
        return database.currentListpurchasedDao()
    }
}