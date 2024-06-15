package com.uleam.appparahelados.data.Topping

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "toppings")
data class Topping(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val precio: Double,
    val cantidad: Int =0
)
