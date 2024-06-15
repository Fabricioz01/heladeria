package com.uleam.appparahelados.data.Pedido.interfaces

import androidx.room.*
import com.uleam.appparahelados.data.Pedido.Pedido
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pedido: Pedido)

    @Update
    suspend fun update(pedido: Pedido)

    @Delete
    suspend fun delete(pedido: Pedido)

    @Query("SELECT * from pedidos WHERE id = :id")
    fun getPedido(id: Int): Flow<Pedido?>

    @Query("SELECT * from pedidos ORDER BY fecha DESC")
    fun getAllPedidos(): Flow<List<Pedido>>
}
