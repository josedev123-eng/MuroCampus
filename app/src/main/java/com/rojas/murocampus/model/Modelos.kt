package com.rojas.murocampus.model

import kotlinx.serialization.Serializable

// Ejemplo resuelto [E]
@Serializable
data class Usuario(
    val id: Int,
    val name: String,
    val email: String
)

// TODO Paso 2: Post, Comentario y NuevoPost
