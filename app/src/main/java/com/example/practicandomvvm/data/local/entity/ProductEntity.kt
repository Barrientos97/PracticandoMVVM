package com.example.practicandomvvm.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "producto",
    indices = [
        Index(value = ["codigo_barra"], unique = true)
    ]
)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
    @ColumnInfo(name = "descripcion")
    var descripcion: String = "",
    @ColumnInfo(name = "codigo_barra")
    var codigobarra:String = "",
    @ColumnInfo(name = "precio")
    var precio: Double = 0.0

)
