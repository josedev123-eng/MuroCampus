package com.rojas.murocampus.data

import com.rojas.murocampus.data.remote.PostApi
import com.rojas.murocampus.model.Comentario
import com.rojas.murocampus.model.NuevoPost
import com.rojas.murocampus.model.Post
import kotlin.coroutines.cancellation.CancellationException

interface PostRepository {
    suspend fun listar(): Result<List<Post>>
    suspend fun detalle(id: Int): Result<Post>
    suspend fun comentarios(id: Int): Result<List<Comentario>>
    suspend fun porUsuario(userId: Int): Result<List<Post>>
    suspend fun crear(nuevo: NuevoPost): Result<Post>
}

class PostRepositoryRemote(private val api: PostApi) : PostRepository {

    override suspend fun listar(): Result<List<Post>> = llamar { api.listar() }

    override suspend fun detalle(id: Int): Result<Post> = llamar { api.detalle(id) }

    override suspend fun comentarios(id: Int): Result<List<Comentario>> = llamar { api.comentarios(id) }

    override suspend fun porUsuario(userId: Int): Result<List<Post>> = llamar { api.porUsuario(userId) }

    override suspend fun crear(nuevo: NuevoPost): Result<Post> = llamar { api.crear(nuevo) }

    // Un solo try/catch para todas las llamadas
    private suspend fun <T> llamar(bloque: suspend () -> T): Result<T> =
        try {
            Result.success(bloque())
        } catch (e: CancellationException) {
            throw e // la cancelacion no se convierte en error
        } catch (e: Exception) {
            Result.failure(e)
        }
}
