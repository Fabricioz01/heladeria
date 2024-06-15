package com.uleam.appparahelados.data.Topping.interfaces

import androidx.room.*
import com.uleam.appparahelados.data.Topping.Topping
import kotlinx.coroutines.flow.Flow

@Dao
interface ToppingDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(topping: Topping)

    @Update
    suspend fun update(topping: Topping)

    @Delete
    suspend fun delete(topping: Topping)

    @Query("SELECT * from toppings WHERE id = :id")
    fun getTopping(id: Int): Flow<Topping?>

    @Query("SELECT * from toppings ORDER BY nombre ASC")
    fun getAllToppings(): Flow<List<Topping>>
}
