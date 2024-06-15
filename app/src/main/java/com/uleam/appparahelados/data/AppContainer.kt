/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uleam.appparahelados.data

import android.content.Context
import com.uleam.appparahelados.data.Helado.OfflineHeladosRepository
import com.uleam.appparahelados.data.Helado.interfaces.HeladoDao
import com.uleam.appparahelados.data.Helado.interfaces.HeladoRepository
import com.uleam.appparahelados.data.HeladoDatabase
import com.uleam.appparahelados.data.HeladoPersonalizado.OfflineHeladosPersonalizadosRepository
import com.uleam.appparahelados.data.HeladoPersonalizado.interfaces.HeladoPersonalizadoDao
import com.uleam.appparahelados.data.HeladoPersonalizado.interfaces.HeladoPersonalizadoRepository
import com.uleam.appparahelados.data.HeladoTopping.OfflineHeladoToppingsRepository
import com.uleam.appparahelados.data.HeladoTopping.interfaces.HeladoToppingDao
import com.uleam.appparahelados.data.HeladoTopping.interfaces.HeladoToppingRepository
import com.uleam.appparahelados.data.Pedido.OfflinePedidoRepository
import com.uleam.appparahelados.data.Pedido.interfaces.PedidoRepository
import com.uleam.appparahelados.data.Topping.OfflineToppingsRepository
import com.uleam.appparahelados.data.Topping.interfaces.ToppingDao
import com.uleam.appparahelados.data.Topping.interfaces.ToppingRepository
import com.uleam.appparahelados.data.Usuario.OfflineUsuariosRepository
import com.uleam.appparahelados.data.Usuario.interfaces.UsuarioDao
import com.uleam.appparahelados.data.Usuario.interfaces.UsuarioRepository

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val usuarioRepository: UsuarioRepository
    val heladoRepository: HeladoRepository
    val toppingRepository: ToppingRepository
    val heladoPersonalizadoRepository: HeladoPersonalizadoRepository
    val heladoToppingRepository: HeladoToppingRepository
    val pedido: PedidoRepository

}

/**
 * [AppContainer] implementation that provides instances of DAOs.
 */
class AppDataContainer(private val context: Context) : AppContainer {
    /**
     * Implementation for [UsuarioDao]
     */
    override val usuarioRepository: UsuarioRepository by lazy {
        OfflineUsuariosRepository(HeladoDatabase.getDatabase(context).usuarioDao())
    }

    /**
     * Implementation for [HeladoDao]
     */
    override val heladoRepository: HeladoRepository by lazy {
        OfflineHeladosRepository(HeladoDatabase.getDatabase(context).heladoDao())
    }

    /**
     * Implementation for [ToppingDao]
     */
    override val toppingRepository: ToppingRepository by lazy {
        OfflineToppingsRepository(HeladoDatabase.getDatabase(context).toppingDao())
    }

    /**
     * Implementation for [HeladoPersonalizadoDao]
     */
    override val heladoPersonalizadoRepository: HeladoPersonalizadoRepository by lazy {
        OfflineHeladosPersonalizadosRepository(HeladoDatabase.getDatabase(context).heladoPersonalizadoDao())
    }

    /**
     * Implementation for [HeladoToppingDao]
     */
    override val heladoToppingRepository: HeladoToppingRepository by lazy {
        OfflineHeladoToppingsRepository(HeladoDatabase.getDatabase(context).heladoToppingDao())
    }

    override val pedido: PedidoRepository by lazy {
        OfflinePedidoRepository(HeladoDatabase.getDatabase(context).pedidoDao())
    }
}
