package com.uleam.appparahelados.data.Helado

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "helados")
data class Helado(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val sabor: String,
    val descripcion: String,
    val precioBase: Double,
    val cantidad: Int =0
)
