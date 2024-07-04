package com.example.practicandomvvm.data.remote.api

import com.example.practicandomvvm.data.remote.dto.ApiResponse
import com.example.practicandomvvm.data.remote.dto.ProductDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface  ProductApi {

    @GET("producto.php/{dato}")
    suspend fun getProducts(@Path("dato") dato:String) : ApiResponse<List<ProductDto>>

    @GET("obtenerProducto.php/{id}")
    suspend fun getProductById(@Path("id") id:Int) : ApiResponse<ProductDto>

    @POST("producto.php")
    suspend fun insert(@Body dto: ProductDto) : ApiResponse<Int>

    @PUT("producto.php")
    suspend fun update(@Body dto: ProductDto) : ApiResponse<Int>

    @DELETE("producto.php/{id}")
    suspend fun delete(@Path("id") id:Int) : ApiResponse<Int>
}