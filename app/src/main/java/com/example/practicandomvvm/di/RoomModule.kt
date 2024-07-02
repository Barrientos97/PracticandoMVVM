package com.example.practicandomvvm.di

import android.content.Context
import androidx.room.Room
import com.example.practicandomvvm.data.local.dao.ProductDao
import com.example.practicandomvvm.data.local.database.AppDatabase
import com.example.practicandomvvm.data.repository.ProductRepositoryImpl
import com.example.practicandomvvm.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val DATAASE_NAME = "appDatabase"

    // Proveer la db (appDatabase)
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATAASE_NAME
        ).build()
    }
    // Proveer los dao
    @Singleton
    @Provides
    fun provideProductDao(db: AppDatabase): ProductDao {
        return db.productDao()
    }

    // Proveer el repositorio
    @Singleton
    @Provides
    fun provideProductRepository(dao: ProductDao): ProductRepository {
        return ProductRepositoryImpl(dao)
    }
}

