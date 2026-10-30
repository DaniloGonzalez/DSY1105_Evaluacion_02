package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.navigation.AppRoute
import com.example.ev2_master_martini.ui.components.AppBottomBar
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun CatalogScreen(
    onBack: () -> Unit,
    onContentClick: () -> Unit,
    onHomeClick: () -> Unit,
    onEventsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "Catálogo",
                    onBack = onBack
                )
                AppBottomBar(
                    selectedRoute = AppRoute.Catalog.route,
                    onHomeClick = onHomeClick,
                    onCatalogClick = {},
                    onEventsClick = onEventsClick,
                    onProfileClick = onProfileClick
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Catálogo",
                style = MaterialTheme.typography.headlineMedium
            )

            Text("Explora nuestros contenidos técnicos.")

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
                    Text("Aprende técnicas y productos para mejorar tus preparaciones.")
                    Text("Ver detalle")
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
                    Text("Recetas y recomendaciones para tu negocio.")
                    Text("Ver detalle")
                }
            }

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogScreenPreview() {
    Ev2_Master_MartiniTheme {
        CatalogScreen(
            onBack = {},
            onContentClick = {},
            onHomeClick = {},
            onEventsClick = {},
            onProfileClick = {}
        )
    }
}
