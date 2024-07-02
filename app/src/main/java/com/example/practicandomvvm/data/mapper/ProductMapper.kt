package com.example.practicandomvvm.data.mapper

import com.example.practicandomvvm.data.local.entity.ProductEntity
import com.example.practicandomvvm.domain.model.Product

object ProductMapper {

    fun toDomain(entity: ProductEntity): Product {
        return   Product(
            id = entity.id,
            descripcion = entity.descripcion,
            codigoBarra = entity.codigobarra,
            precio = entity.precio
        )
    }

    fun toDatabase(model: Product): ProductEntity {
        return ProductEntity(
            id = model.id,
            descripcion = model.descripcion,
            codigobarra = model.codigoBarra,
            precio = model.precio
        )
    }

}