package com.uleam.appparahelados.data.Pedido

import com.uleam.appparahelados.data.Pedido.interfaces.PedidoDao
import com.uleam.appparahelados.data.Pedido.interfaces.PedidoRepository
import kotlinx.coroutines.flow.Flow

class OfflinePedidoRepository(private val pedidoDao: PedidoDao) : PedidoRepository {
    override fun getAllPedidosStream(): Flow<List<Pedido>> = pedidoDao.getAllPedidos()

    override fun getPedidoStream(id: Int): Flow<Pedido?> = pedidoDao.getPedido(id)

    override suspend fun insertPedido(pedido: Pedido) = pedidoDao.insert(pedido)

    override suspend fun deletePedido(pedido: Pedido) = pedidoDao.delete(pedido)

    override suspend fun updatePedido(pedido: Pedido) = pedidoDao.update(pedido)
}
