package com.rojas.murocampus.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rojas.murocampus.viewmodel.PostsViewModel
import com.rojas.murocampus.viewmodel.UiState
import androidx.compose.ui.unit.dp

@Composable
fun PantallaLista(vm: PostsViewModel, onAbrir: (Int) -> Unit) {
    val estado by vm.lista.collectAsStateWithLifecycle()

    when (val e = estado) {
        is UiState.Cargando -> Cargando()
        is UiState.Error -> ErrorConReintento(e.mensaje, onReintentar = { vm.cargar() })
        is UiState.Exito -> {
            LazyColumn(contentPadding = PaddingValues(vertical = 6.dp)) {
                items(e.datos) { post ->
                    TarjetaPost(
                        usuario = post.userId,
                        titulo = post.title,
                        cuerpo = post.body,
                        maxLineas = 2,
                        onClick = { onAbrir(post.id) }
                    )
                }
            }
        }
    }
}
