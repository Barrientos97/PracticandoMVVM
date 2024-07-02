package com.example.practicandomvvm.data.repository

import com.example.practicandomvvm.data.local.dao.ProductDao
import com.example.practicandomvvm.data.mapper.ProductMapper
import com.example.practicandomvvm.domain.model.Product
import com.example.practicandomvvm.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao
): ProductRepository {
    override fun getList(dato: String): Flow<List<Product>> {
        return dao.getList(dato).map { list ->
            list.map { productEntity ->
                ProductMapper.toDomain(productEntity)
            }
        }
    }

    override suspend fun getProductById(id: Int): Product? {
        return dao.getProductById(id)?.let { productEntity ->
            ProductMapper.toDomain(productEntity)
        }
    }

    override suspend fun save(model: Product): Int {
        return if (model.id == 0) {
            dao.insert(ProductMapper.toDatabase(model)).toInt()
        } else {
            dao.update(ProductMapper.toDatabase(model))
        }
    }

    override suspend fun delete(model: Product): Int {
        return dao.delete(ProductMapper.toDatabase(model))
    }
}