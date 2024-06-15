package com.uleam.appparahelados.data.Pedido.interfaces

import com.uleam.appparahelados.data.Pedido.Pedido
import kotlinx.coroutines.flow.Flow

interface PedidoRepository {
    fun getAllPedidosStream(): Flow<List<Pedido>>
    fun getPedidoStream(id: Int): Flow<Pedido?>
    suspend fun insertPedido(pedido: Pedido)
    suspend fun deletePedido(pedido: Pedido)
    suspend fun updatePedido(pedido: Pedido)
}
