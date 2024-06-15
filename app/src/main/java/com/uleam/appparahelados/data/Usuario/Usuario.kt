package com.uleam.appparahelados.data.Usuario

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "usuarios")
data class Usuario(
    val nombre: String,
    val correo: String,
    val pass: String,
    val telefono: String,
    val direccion: String,
    val rol: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
