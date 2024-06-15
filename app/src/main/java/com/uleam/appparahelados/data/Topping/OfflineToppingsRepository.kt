package com.uleam.appparahelados.data.Topping

import com.uleam.appparahelados.data.Topping.interfaces.ToppingDao
import com.uleam.appparahelados.data.Topping.interfaces.ToppingRepository
import kotlinx.coroutines.flow.Flow

class OfflineToppingsRepository(private val toppingDao: ToppingDao) : ToppingRepository {
    override fun getAllToppingsStream(): Flow<List<Topping>> = toppingDao.getAllToppings()

    override fun getToppingStream(id: Int): Flow<Topping?> = toppingDao.getTopping(id)

    override suspend fun insertTopping(topping: Topping) = toppingDao.insert(topping)

    override suspend fun deleteTopping(topping: Topping) = toppingDao.delete(topping)

    override suspend fun updateTopping(topping: Topping) = toppingDao.update(topping)
}
