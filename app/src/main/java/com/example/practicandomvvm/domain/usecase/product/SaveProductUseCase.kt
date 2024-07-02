package com.example.practicandomvvm.domain.usecase.product

import com.example.practicandomvvm.domain.model.Product
import com.example.practicandomvvm.domain.repository.ProductRepository
import javax.inject.Inject

class SaveProductUseCase@Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(model: Product) = repository.save(model)
}
