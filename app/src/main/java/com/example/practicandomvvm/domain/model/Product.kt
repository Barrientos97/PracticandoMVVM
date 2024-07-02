package com.example.practicandomvvm.domain.model

data class Product (
    var id: Int = 0,
    var descripcion: String = "",
    var codigoBarra:String = "",
    var precio: Double = 0.0
)