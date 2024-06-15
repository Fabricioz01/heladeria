package com.uleam.appparahelados.data.HeladoPersonalizado.interfaces

import com.uleam.appparahelados.data.HeladoPersonalizado.HeladoPersonalizado
import kotlinx.coroutines.flow.Flow

interface HeladoPersonalizadoRepository {

    fun getAllHeladosPersonalizadosStream(): Flow<List<HeladoPersonalizado>>
    fun getHeladoPersonalizadoStream(id: Int): Flow<HeladoPersonalizado?>
    suspend fun insertHeladoPersonalizado(heladoPersonalizado: HeladoPersonalizado)
    suspend fun deleteHeladoPersonalizado(heladoPersonalizado: HeladoPersonalizado)
    suspend fun updateHeladoPersonalizado(heladoPersonalizado: HeladoPersonalizado)

}