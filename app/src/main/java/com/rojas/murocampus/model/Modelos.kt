package com.rojas.murocampus.model

import kotlinx.serialization.Serializable

// Ejemplo resuelto [E]
@Serializable
data class Usuario(
    val id: Int,
    val name: String,
    val email: String
)

@Serializable
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

@Serializable
data class Comentario(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)

// Lo que enviamos al crear: no lleva id, el servidor lo asigna
@Serializable
data class NuevoPost(
    val userId: Int,
    val title: String,
    val body: String
)
