package com.uleam.appparahelados.data.HeladoPersonalizado.interfaces

import androidx.room.*
import com.uleam.appparahelados.data.HeladoPersonalizado.HeladoPersonalizado
import kotlinx.coroutines.flow.Flow

@Dao
interface HeladoPersonalizadoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(heladoPersonalizado: HeladoPersonalizado)

    @Update
    suspend fun update(heladoPersonalizado: HeladoPersonalizado)

    @Delete
    suspend fun delete(heladoPersonalizado: HeladoPersonalizado)

    @Query("SELECT * from helados_personalizados WHERE id = :id")
    fun getHeladoPersonalizado(id: Int): Flow<HeladoPersonalizado?>

    @Query("SELECT * from helados_personalizados ORDER BY nombre ASC")
    fun getAllHeladosPersonalizados(): Flow<List<HeladoPersonalizado>>
}
