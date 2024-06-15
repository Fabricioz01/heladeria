package com.uleam.appparahelados.data.HeladoPersonalizado


import com.uleam.appparahelados.data.HeladoPersonalizado.interfaces.HeladoPersonalizadoDao
import com.uleam.appparahelados.data.HeladoPersonalizado.interfaces.HeladoPersonalizadoRepository
import kotlinx.coroutines.flow.Flow

class OfflineHeladosPersonalizadosRepository(private val heladoPersonalizadoDao: HeladoPersonalizadoDao) :
    HeladoPersonalizadoRepository {
    override fun getAllHeladosPersonalizadosStream(): Flow<List<HeladoPersonalizado>> = heladoPersonalizadoDao.getAllHeladosPersonalizados()

    override fun getHeladoPersonalizadoStream(id: Int): Flow<HeladoPersonalizado?> = heladoPersonalizadoDao.getHeladoPersonalizado(id)

    override suspend fun insertHeladoPersonalizado(heladoPersonalizado: HeladoPersonalizado) = heladoPersonalizadoDao.insert(heladoPersonalizado)

    override suspend fun deleteHeladoPersonalizado(heladoPersonalizado: HeladoPersonalizado) = heladoPersonalizadoDao.delete(heladoPersonalizado)

    override suspend fun updateHeladoPersonalizado(heladoPersonalizado: HeladoPersonalizado) = heladoPersonalizadoDao.update(heladoPersonalizado)
}
