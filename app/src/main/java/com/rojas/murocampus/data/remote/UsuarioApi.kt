package com.rojas.murocampus.data.remote

import com.rojas.murocampus.model.Usuario
import retrofit2.http.GET

interface UsuarioApi {
    @GET("users")
    suspend fun listar(): List<Usuario>
}
