package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.components.VideoPlayer
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun ContentDetailScreen(
    onBack: () -> Unit,
    onContactSales: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Detalle del contenido",
                onBack = onBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Curso de chocolatería",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Aprende técnicas de chocolatería y conoce los productos recomendados para tus preparaciones.",
                style = MaterialTheme.typography.bodyLarge
            )

            // CAMBIO REALIZADO: reproductor multimedia para el video técnico.
            // URL de video de ejemplo de ExoPlayer (dato ficticio de muestra).
            VideoPlayer(
                videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4"
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Ficha técnica",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Contenido: video técnico y ficha de productos."
                    )
                    Text(
                        text = "Duración estimada: 5 minutos.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Button(
                onClick = onContactSales,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Solicitar asesoría")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentDetailScreenPreview() {
    Ev2_Master_MartiniTheme {
        ContentDetailScreen(
            onBack = {},
            onContactSales = {}
        )
    }
}