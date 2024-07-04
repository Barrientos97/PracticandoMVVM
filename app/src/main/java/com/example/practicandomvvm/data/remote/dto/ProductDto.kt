package com.example.practicandomvvm.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ProductDto(

    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("descripcion")
    var descripcion: String = "",
    @SerializedName("codigobarra")
    var codigobarra: String = "",
    @SerializedName("precio")
    var precio: Double = 0.0
)
