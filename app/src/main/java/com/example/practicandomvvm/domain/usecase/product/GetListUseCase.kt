package com.example.practicandomvvm.domain.usecase.product

import com.example.practicandomvvm.domain.repository.ProductRepository
import javax.inject.Inject

class GetListUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    operator fun invoke(dato: String) = repository.getList(dato)
}