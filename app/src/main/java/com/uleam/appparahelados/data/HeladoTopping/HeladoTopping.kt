package com.uleam.appparahelados.data.HeladoTopping

import androidx.room.Entity
import androidx.room.ForeignKey
import com.uleam.appparahelados.data.HeladoPersonalizado.HeladoPersonalizado
import com.uleam.appparahelados.data.Topping.Topping

@Entity(
    tableName = "helado_toppings",
    primaryKeys = ["heladoPersonalizadoId", "toppingId"],
    foreignKeys = [
        ForeignKey(entity = HeladoPersonalizado::class, parentColumns = ["id"], childColumns = ["heladoPersonalizadoId"]),
        ForeignKey(entity = Topping::class, parentColumns = ["id"], childColumns = ["toppingId"])
    ]
)
data class HeladoTopping(
    val heladoPersonalizadoId: Int,
    val toppingId: Int,

)
