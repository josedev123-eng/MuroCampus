package com.rojas.murocampus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rojas.murocampus.data.PostRepository
import com.rojas.murocampus.model.Comentario
import com.rojas.murocampus.model.Post
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetalleUi(val post: Post, val comentarios: List<Comentario>)

class PostsViewModel(private val repo: PostRepository) : ViewModel() {

    private val _lista = MutableStateFlow<UiState<List<Post>>>(UiState.Cargando)
    val lista = _lista.asStateFlow()

    private val _detalle = MutableStateFlow<UiState<DetalleUi>>(UiState.Cargando)
    val detalle = _detalle.asStateFlow()

    private var trabajoDetalle: Job? = null

    init { cargar() }

    fun cargar() {
        viewModelScope.launch {
            _lista.value = UiState.Cargando
            repo.listar()
                .onSuccess { _lista.value = UiState.Exito(it) }
                .onFailure { _lista.value = UiState.Error(mensajeDe(it)) }
        }
    }

    fun abrir(id: Int) {
        trabajoDetalle?.cancel() // si se abre otro post, se cancela el anterior
        trabajoDetalle = viewModelScope.launch {
            _detalle.value = UiState.Cargando
            // se piden las dos cosas a la vez
            val pedidoPost = async { repo.detalle(id) }
            val pedidoComentarios = async { repo.comentarios(id) }
            val resultadoPost = pedidoPost.await()
            val resultadoComentarios = pedidoComentarios.await()

            val post = resultadoPost.getOrNull()
            val comentarios = resultadoComentarios.getOrNull()
            if (post != null && comentarios != null) {
                _detalle.value = UiState.Exito(DetalleUi(post, comentarios))
            } else {
                val error = resultadoPost.exceptionOrNull() ?: resultadoComentarios.exceptionOrNull()
                _detalle.value = UiState.Error(mensajeDe(error ?: Exception()))
            }
        }
    }
}
