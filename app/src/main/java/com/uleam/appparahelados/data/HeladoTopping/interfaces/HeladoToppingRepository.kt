package com.uleam.appparahelados.data.HeladoTopping.interfaces

import com.uleam.appparahelados.data.HeladoTopping.HeladoTopping
import kotlinx.coroutines.flow.Flow

interface HeladoToppingRepository {

    fun getAllHeladoToppingsStream(): Flow<List<HeladoTopping>>
    fun getHeladoToppingStream(id: Int): Flow<HeladoTopping?>
    suspend fun insertHeladoTopping(heladoTopping: HeladoTopping)
    suspend fun deleteHeladoTopping(heladoTopping: HeladoTopping)
    suspend fun updateHeladoTopping(heladoTopping: HeladoTopping)
}