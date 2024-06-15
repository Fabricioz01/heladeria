package com.uleam.appparahelados.data.Topping.interfaces

import com.uleam.appparahelados.data.Topping.Topping
import kotlinx.coroutines.flow.Flow

interface ToppingRepository {
    fun getAllToppingsStream(): Flow<List<Topping>>
    fun getToppingStream(id: Int): Flow<Topping?>
    suspend fun insertTopping(topping: Topping)
    suspend fun deleteTopping(topping: Topping)
    suspend fun updateTopping(topping: Topping)

}