package com.uleam.appparahelados.data.HeladoTopping.interfaces

import androidx.room.*
import com.uleam.appparahelados.data.HeladoTopping.HeladoTopping
import kotlinx.coroutines.flow.Flow

@Dao
interface HeladoToppingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(heladoTopping: HeladoTopping)

    @Update
    suspend fun update(heladoTopping: HeladoTopping)

    @Delete
    suspend fun delete(heladoTopping: HeladoTopping)

    @Query("SELECT * from helado_toppings WHERE toppingId = :id")
    fun getHeladoTopping(id: Int): Flow<HeladoTopping?>

    @Query("SELECT * from helado_toppings ORDER BY heladoPersonalizadoId ASC")
    fun getAllHeladoToppings(): Flow<List<HeladoTopping>>
}
