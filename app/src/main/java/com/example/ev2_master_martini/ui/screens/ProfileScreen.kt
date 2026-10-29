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
fun ProfileScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Mi perfil",
            style = MaterialTheme.typography.headlineMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Datos personales",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(text = "Nombre: Usuario Demo")
                Text(text = "Correo: usuario@ejemplo.cl")
                Text(text = "Teléfono: +56 9 1234 5678")
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Mis intereses",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(text = "Tipo de negocio: Pastelería")
                Text(text = "Tamaño: Pequeño")
                Text(text = "Contenido: Recetas")
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
fun ProfileScreenPreview() {
    Ev2_Master_MartiniTheme {
        ProfileScreen(
            onBack = {}
        )
    }
}
