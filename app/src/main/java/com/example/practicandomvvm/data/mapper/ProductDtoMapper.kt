package com.example.practicandomvvm.data.mapper

import com.example.practicandomvvm.data.remote.dto.ProductDto
import com.example.practicandomvvm.domain.model.Product

object ProductDtoMapper {

    fun toDomain(entity: ProductDto): Product {
        return   Product(
            id = entity.id,
            descripcion = entity.descripcion,
            codigoBarra = entity.codigobarra,
            precio = entity.precio
        )
    }

    fun toDatabase(model: Product): ProductDto {
        return ProductDto(
            id = model.id,
            descripcion = model.descripcion,
            codigobarra = model.codigoBarra,
            precio = model.precio
        )
    }
}