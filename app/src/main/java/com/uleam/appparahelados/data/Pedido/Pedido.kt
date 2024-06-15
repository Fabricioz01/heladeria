package com.uleam.appparahelados.data.Pedido

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.uleam.appparahelados.data.HeladoPersonalizado.HeladoPersonalizado
import com.uleam.appparahelados.data.Usuario.Usuario

@Entity(
    tableName = "pedidos",
    foreignKeys = [
        ForeignKey(entity = Usuario::class, parentColumns = ["id"], childColumns = ["usuarioId"]),
        ForeignKey(entity = HeladoPersonalizado::class, parentColumns = ["id"], childColumns = ["heladoPersonalizadoId"])
    ]
)
data class Pedido(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioId: Int,
    val heladoPersonalizadoId: Int,
    val fecha: String,
    val estado: String,
    val cantidad: Int,
    val precioTotal: Double
)
