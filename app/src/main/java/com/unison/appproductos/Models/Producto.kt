package com.unison.appproductos.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Producto(
    @PrimaryKey val id: String,
    val nombre: String,
    val descripcion: String,
    val fechaRegistro: String,
    val precio: Double
)
