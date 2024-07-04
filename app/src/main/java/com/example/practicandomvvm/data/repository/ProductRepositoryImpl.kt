package com.example.practicandomvvm.data.repository

import com.example.practicandomvvm.data.local.dao.ProductDao
import com.example.practicandomvvm.data.mapper.ProductDtoMapper
import com.example.practicandomvvm.data.mapper.ProductMapper
import com.example.practicandomvvm.data.remote.api.ProductApi
import com.example.practicandomvvm.domain.model.Product
import com.example.practicandomvvm.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val dao: ProductDao,
    private val api: ProductApi
): ProductRepository {
    override fun getList(dato: String): Flow<List<Product>> {
        return dao.getList(dato).map { list ->
            list.map { productEntity ->
                ProductMapper.toDomain(productEntity)
            }
        }
        /*return flow{
            emit(
               api.getProducts(dato).data.map {
                   ProductDtoMapper.toDomain(it)
               }
            )
        }*/
    }

    override suspend fun getProductById(id: Int): Product? {
        /*return dao.getProductById(id)?.let { productEntity ->
            ProductMapper.toDomain(productEntity)
        }*/
        return api.getProductById(id).let {
            ProductDtoMapper.toDomain(it.data)
        }
    }

    override suspend fun save(model: Product): Int {
       /* return if (model.id == 0) {
            dao.insert(ProductMapper.toDatabase(model)).toInt()
        } else {
            dao.update(ProductMapper.toDatabase(model))
        }*/

        return if (model.id == 0) {
            api.insert(ProductDtoMapper.toDatabase(model)).data
        } else {
            api.update(ProductDtoMapper.toDatabase(model)).data
        }
    }

    override suspend fun delete(model: Product): Int {
//        return dao.delete(ProductMapper.toDatabase(model))
        return api.delete(model.id).data
    }

    override suspend fun getListApi(dato: String): List<Product> {
        val apiDataResponse = api.getProducts(dato).data.map {
            ProductDtoMapper.toDomain(it)
        }
        dao.deleteAll()
        dao.insertAll(
            apiDataResponse.map {
                ProductMapper.toDatabase(it)
            }
        )
        return apiDataResponse
    }
}