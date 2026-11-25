package com.rojas.murocampus.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rojas.murocampus.ui.theme.VerdeFondo
import com.rojas.murocampus.ui.theme.VerdeTexto
import com.rojas.murocampus.viewmodel.PostsViewModel
import com.rojas.murocampus.viewmodel.UiState

@Composable
fun PantallaCrear(vm: PostsViewModel) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var contenido by rememberSaveable { mutableStateOf("") }
    val estado by vm.creado.collectAsStateWithLifecycle()

    // si se publico bien, se limpia el formulario
    LaunchedEffect(estado) {
        if (estado is UiState.Exito) {
            titulo = ""
            contenido = ""
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contenido,
            onValueChange = { contenido = it },
            label = { Text("Contenido") },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))

        // deshabilitado si falta el titulo o el contenido
        Button(
            onClick = { vm.publicar(titulo, contenido) },
            enabled = titulo.isNotBlank() && contenido.isNotBlank() && estado !is UiState.Cargando
        ) {
            Text("Publicar", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(12.dp))

        when (val e = estado) {
            null -> {}
            is UiState.Cargando -> CircularProgressIndicator()
            is UiState.Exito -> Text(
                text = "Publicado con id ${e.datos.id}",
                color = VerdeTexto,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(VerdeFondo, RoundedCornerShape(10.dp))
                    .drawBehind {
                        // borde punteado verde
                        drawRoundRect(
                            color = Color(0xFF2E7D32),
                            cornerRadius = CornerRadius(10.dp.toPx()),
                            style = Stroke(
                                width = 1.5.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f))
                            )
                        )
                    }
                    .padding(12.dp)
            )
            is UiState.Error -> Text(
                text = e.mensaje,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(8.dp))
                    .padding(10.dp)
            )
        }
    }
}
