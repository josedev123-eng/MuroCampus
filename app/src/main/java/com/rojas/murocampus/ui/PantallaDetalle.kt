package com.rojas.murocampus.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rojas.murocampus.ui.theme.BordeTarjeta
import com.rojas.murocampus.ui.theme.FondoTarjeta
import com.rojas.murocampus.ui.theme.TextoGris
import com.rojas.murocampus.viewmodel.PostsViewModel
import com.rojas.murocampus.viewmodel.UiState

@Composable
fun PantallaDetalle(vm: PostsViewModel, id: Int) {
    // cada vez que cambia el id se pide el detalle
    LaunchedEffect(id) { vm.abrir(id) }

    val estado by vm.detalle.collectAsStateWithLifecycle()

    when (val e = estado) {
        is UiState.Cargando -> Cargando("Cargando publicación…")
        is UiState.Error -> ErrorConReintento(e.mensaje, onReintentar = { vm.abrir(id) })
        is UiState.Exito -> {
            val post = e.datos.post
            val comentarios = e.datos.comentarios
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, BordeTarjeta),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = post.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1C1C1E)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(text = post.body, fontSize = 14.sp, color = TextoGris)
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Comentarios (${comentarios.size})",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }
                items(comentarios) { comentario ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = FondoTarjeta)
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
                            Text(
                                text = comentario.email,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(text = comentario.body, fontSize = 14.sp, color = Color(0xFF3A3F45))
                        }
                    }
                }
            }
        }
    }
}
