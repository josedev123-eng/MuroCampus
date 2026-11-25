package com.rojas.murocampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rojas.murocampus.ui.PantallaDetalle
import com.rojas.murocampus.ui.PantallaLista
import com.rojas.murocampus.ui.theme.MuroCampusTheme
import com.rojas.murocampus.viewmodel.PostsViewModel
import com.rojas.murocampus.viewmodel.PostsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))
        setContent {
            MuroCampusTheme {
                val vm: PostsViewModel = viewModel(factory = PostsViewModelFactory())
                // null = se ve la lista, un numero = se ve el detalle de ese post
                var idSeleccionado by rememberSaveable { mutableStateOf<Int?>(null) }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { BarraSuperior() }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        val id = idSeleccionado
                        if (id != null) {
                            BackHandler { idSeleccionado = null }
                            PantallaDetalle(vm, id)
                        } else {
                            PantallaLista(vm, onAbrir = { idSeleccionado = it })
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior() {
    TopAppBar(
        title = { Text("MuroCampus", fontWeight = FontWeight.Bold) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
