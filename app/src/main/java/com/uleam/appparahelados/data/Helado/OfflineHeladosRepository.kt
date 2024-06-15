package com.uleam.appparahelados.data.Helado

import com.uleam.appparahelados.data.Helado.interfaces.HeladoRepository
import com.uleam.appparahelados.data.Helado.interfaces.HeladoDao
import kotlinx.coroutines.flow.Flow

class OfflineHeladosRepository(private val heladoDao: HeladoDao) : HeladoRepository {
    override fun getAllHeladosStream(): Flow<List<Helado>> = heladoDao.getAllHelados()

    override fun getHeladoStream(id: Int): Flow<Helado?> = heladoDao.getHelado(id)

    override suspend fun insertHelado(helado: Helado) = heladoDao.insert(helado)

    override suspend fun deleteHelado(helado: Helado) = heladoDao.delete(helado)

    override suspend fun updateHelado(helado: Helado) = heladoDao.update(helado)
}
