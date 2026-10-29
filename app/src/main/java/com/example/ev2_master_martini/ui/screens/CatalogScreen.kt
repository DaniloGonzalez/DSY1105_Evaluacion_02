package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun CatalogScreen(
    onBack: () -> Unit,
    onContentClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Catálogo",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Explora nuestros contenidos técnicos."
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = onContentClick
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Curso de chocolatería",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Aprende técnicas y productos para mejorar tus preparaciones."
                )

                Text(
                    text = "Ver detalle"
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            onClick = onContentClick
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Recetas para pastelería",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Recetas y recomendaciones para tu negocio."
                )

                Text(
                    text = "Ver detalle"
                )
            }
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver al inicio")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogScreenPreview() {
    Ev2_Master_MartiniTheme {
        CatalogScreen(
            onBack = {},
            onContentClick = {}
        )
    }
}
