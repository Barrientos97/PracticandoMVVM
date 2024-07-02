package com.example.practicandomvvm.domain.usecase.product

import com.example.practicandomvvm.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUserCase @Inject constructor(
    private val repository: ProductRepository
){
    suspend operator fun invoke(id: Int) = repository.getProductById(id)
}