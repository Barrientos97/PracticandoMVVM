package com.example.practicandomvvm.domain.repository

import com.example.practicandomvvm.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getList(dato: String): Flow<List<Product>>

    suspend fun getProductById(id: Int): Product?

    suspend fun save(model: Product): Int

    suspend fun delete(model: Product): Int
}