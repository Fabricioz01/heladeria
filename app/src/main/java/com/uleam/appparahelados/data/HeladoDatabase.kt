package com.uleam.appparahelados.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.uleam.appparahelados.data.Helado.Helado
import com.uleam.appparahelados.data.Helado.interfaces.HeladoDao
import com.uleam.appparahelados.data.HeladoPersonalizado.HeladoPersonalizado
import com.uleam.appparahelados.data.HeladoPersonalizado.interfaces.HeladoPersonalizadoDao
import com.uleam.appparahelados.data.HeladoTopping.HeladoTopping
import com.uleam.appparahelados.data.HeladoTopping.interfaces.HeladoToppingDao
import com.uleam.appparahelados.data.Pedido.Pedido
import com.uleam.appparahelados.data.Pedido.interfaces.PedidoDao
import com.uleam.appparahelados.data.Topping.Topping
import com.uleam.appparahelados.data.Topping.interfaces.ToppingDao
import com.uleam.appparahelados.data.Usuario.Usuario
import com.uleam.appparahelados.data.Usuario.interfaces.UsuarioDao

/**
 * Database class with a singleton Instance object.
 */
@Database(
    entities = [
        Usuario::class,
        Helado::class,
        Topping::class,
        HeladoPersonalizado::class,
        HeladoTopping::class ,
        Pedido::class
    ],
    version = 1,
    exportSchema = false
)
abstract class HeladoDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun heladoDao(): HeladoDao
    abstract fun toppingDao(): ToppingDao
    abstract fun heladoPersonalizadoDao(): HeladoPersonalizadoDao
    abstract fun heladoToppingDao(): HeladoToppingDao
    abstract fun pedidoDao(): PedidoDao

    companion object {
        @Volatile
        private var INSTANCE: HeladoDatabase? = null

        fun getDatabase(context: Context): HeladoDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    HeladoDatabase::class.java,
                    "helado_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
