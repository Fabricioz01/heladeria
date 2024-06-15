package com.uleam.appparahelados.data.Helado.interfaces

import androidx.room.*
import com.uleam.appparahelados.data.Helado.Helado
import kotlinx.coroutines.flow.Flow

@Dao
interface HeladoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(helado: Helado)

    @Update
    suspend fun update(helado: Helado)

    @Delete
    suspend fun delete(helado: Helado)

    @Query("SELECT * from helados WHERE id = :id")
    fun getHelado(id: Int): Flow<Helado?>

    @Query("SELECT * from helados ORDER BY nombre ASC")
    fun getAllHelados(): Flow<List<Helado>>
}
