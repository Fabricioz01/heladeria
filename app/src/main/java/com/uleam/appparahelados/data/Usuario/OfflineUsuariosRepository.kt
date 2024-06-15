package com.uleam.appparahelados.data.Usuario

import com.uleam.appparahelados.data.Usuario.interfaces.UsuarioDao
import com.uleam.appparahelados.data.Usuario.interfaces.UsuarioRepository
import kotlinx.coroutines.flow.Flow

class OfflineUsuariosRepository(private val usuarioDao: UsuarioDao) : UsuarioRepository {
    override fun getAllUsuariosStream(): Flow<List<Usuario>> = usuarioDao.getAllUsuarios()

    override fun getUsuarioStream(id: Int): Flow<Usuario?> = usuarioDao.getUsuario(id)

    override suspend fun insertUsuario(usuario: Usuario) = usuarioDao.insert(usuario)

    override suspend fun deleteUsuario(usuario: Usuario) = usuarioDao.delete(usuario)

    override suspend fun updateUsuario(usuario: Usuario) = usuarioDao.update(usuario)
    override suspend fun getUsuarioByUsername(correo: String): Usuario? = usuarioDao.getUsuarioByUsername(correo)

    override suspend fun getUserByEmailAndPassword(email: String, password: String): Usuario? = usuarioDao.getUserByEmailAndPassword(email, password)
    override suspend fun areCredentialsValid(correo: String, password: String): Boolean {
        val user = usuarioDao.getUsuarioByUsername(correo)
        return user != null && user.pass == password
    }

}
