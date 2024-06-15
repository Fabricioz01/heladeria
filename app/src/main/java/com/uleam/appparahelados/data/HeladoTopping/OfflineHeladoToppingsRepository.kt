package com.uleam.appparahelados.data.HeladoTopping


import com.uleam.appparahelados.data.HeladoTopping.interfaces.HeladoToppingDao
import com.uleam.appparahelados.data.HeladoTopping.interfaces.HeladoToppingRepository
import kotlinx.coroutines.flow.Flow

class OfflineHeladoToppingsRepository(private val heladoToppingDao: HeladoToppingDao) :
    HeladoToppingRepository {
    override fun getAllHeladoToppingsStream(): Flow<List<HeladoTopping>> = heladoToppingDao.getAllHeladoToppings()

    override fun getHeladoToppingStream(id: Int): Flow<HeladoTopping?> = heladoToppingDao.getHeladoTopping(id)

    override suspend fun insertHeladoTopping(heladoTopping: HeladoTopping) = heladoToppingDao.insert(heladoTopping)

    override suspend fun deleteHeladoTopping(heladoTopping: HeladoTopping) = heladoToppingDao.delete(heladoTopping)

    override suspend fun updateHeladoTopping(heladoTopping: HeladoTopping) = heladoToppingDao.update(heladoTopping)
}
