package com.rojas.murocampus.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rojas.murocampus.ui.theme.BordeTarjeta
import com.rojas.murocampus.ui.theme.FondoTarjeta
import com.rojas.murocampus.ui.theme.TextoGris

// Color de fondo de la etiqueta de cada usuario (1 al 10)
private val coloresUsuario = listOf(
    Color(0xFF2E7DD1), Color(0xFFF0A500), Color(0xFF2E9E5B), Color(0xFF8E44AD), Color(0xFFD9534F),
    Color(0xFF16A2A2), Color(0xFF8D6E63), Color(0xFF3F51B5), Color(0xFFE67E22), Color(0xFF607D8B)
)

fun colorDeUsuario(usuario: Int): Color = coloresUsuario[(usuario - 1).mod(coloresUsuario.size)]

// Etiqueta "Usuario N" con fondo de color (el amarillo lleva letra oscura)
@Composable
fun EtiquetaUsuario(usuario: Int) {
    val fondo = colorDeUsuario(usuario)
    val letra = if (fondo == Color(0xFFF0A500)) Color(0xFF2B2B2B) else Color.White
    Text(
        text = "Usuario $usuario",
        color = letra,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .background(fondo, RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 1.dp)
    )
}

// Tarjeta de una publicacion: etiqueta del usuario, titulo en negrita y resumen
@Composable
fun TarjetaPost(
    usuario: Int,
    titulo: String,
    cuerpo: String,
    maxLineas: Int = 2,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = FondoTarjeta)
    ) {
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
            EtiquetaUsuario(usuario)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = titulo,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C1C1E),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                // el texto trae saltos de linea, se juntan para el resumen
                text = cuerpo.replace("\n", " "),
                fontSize = 13.sp,
                color = TextoGris,
                maxLines = maxLineas,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// Indicador de carga dentro de un recuadro blanco
@Composable
fun Cargando(texto: String = "Cargando publicaciones…") {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, BordeTarjeta)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    trackColor = BordeTarjeta
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(texto, color = TextoGris, fontSize = 15.sp)
            }
        }
    }
}

// Recuadro de error con el mensaje y debajo el boton Reintentar
@Composable
fun ErrorConReintento(mensaje: String, onReintentar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = mensaje,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.errorContainer, RoundedCornerShape(10.dp))
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = onReintentar) {
            Text("Reintentar", fontWeight = FontWeight.Bold)
        }
    }
}
