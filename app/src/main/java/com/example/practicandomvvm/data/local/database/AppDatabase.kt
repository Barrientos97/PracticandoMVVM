package com.example.practicandomvvm.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.practicandomvvm.data.local.dao.ProductDao
import com.example.practicandomvvm.data.local.entity.ProductEntity


@Database(
    entities = [ProductEntity::class], version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao


}