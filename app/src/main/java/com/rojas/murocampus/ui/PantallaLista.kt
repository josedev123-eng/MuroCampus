package com.rojas.murocampus.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rojas.murocampus.viewmodel.PostsViewModel
import com.rojas.murocampus.viewmodel.UiState

@Composable
fun PantallaLista(vm: PostsViewModel, onAbrir: (Int) -> Unit) {
    val estado by vm.lista.collectAsStateWithLifecycle()
    val filtro by vm.usuarioFiltro.collectAsStateWithLifecycle()

    Column {
        // Fila de chips: Todos y Usuario 1 a Usuario 10
        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = filtro == null,
                    onClick = { vm.filtrar(null) },
                    label = { Text("Todos", fontWeight = FontWeight.Bold) },
                    shape = RoundedCornerShape(50),
                    colors = coloresChip(),
                    border = bordeChip(filtro == null)
                )
            }
            items((1..10).toList()) { numero ->
                FilterChip(
                    selected = filtro == numero,
                    onClick = { vm.filtrar(numero) },
                    label = { Text("Usuario $numero", fontWeight = FontWeight.Bold) },
                    shape = RoundedCornerShape(50),
                    colors = coloresChip(),
                    border = bordeChip(filtro == numero)
                )
            }
        }

        when (val e = estado) {
            is UiState.Cargando -> Cargando()
            is UiState.Error -> ErrorConReintento(e.mensaje, onReintentar = { vm.cargar() })
            is UiState.Exito -> {
                if (e.datos.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Sin publicaciones")
                    }
                } else {
                    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)) {
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
    }
}

@Composable
fun coloresChip() = FilterChipDefaults.filterChipColors(
    containerColor = Color.White,
    labelColor = MaterialTheme.colorScheme.primary,
    selectedContainerColor = MaterialTheme.colorScheme.primary,
    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
)

@Composable
fun bordeChip(seleccionado: Boolean) = FilterChipDefaults.filterChipBorder(
    enabled = true,
    selected = seleccionado,
    borderColor = Color(0xFFB9D3EA),
    selectedBorderColor = MaterialTheme.colorScheme.primary
)
