package com.uleam.appparahelados.data.HeladoPersonalizado

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.uleam.appparahelados.data.Helado.Helado
import com.uleam.appparahelados.data.Usuario.Usuario

@Entity(
    tableName = "helados_personalizados",
    foreignKeys = [
        ForeignKey(entity = Helado::class, parentColumns = ["id"], childColumns = ["heladoId"]),
        ForeignKey(entity = Usuario::class, parentColumns = ["id"], childColumns = ["usuarioId"])
    ]
)
data class HeladoPersonalizado(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val heladoId: Int,
    val usuarioId: Int,
    val precioTotal: Double,
    val cantidad: Int =0
)
