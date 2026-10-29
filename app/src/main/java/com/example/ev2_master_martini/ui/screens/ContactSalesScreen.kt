package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun ContactSalesScreen(
    onBack: () -> Unit
) {
    var requestSent by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Contactar ventas",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Solicita orientación sobre productos, cursos o recetas."
        )

        Button(
            onClick = {
                requestSent = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Enviar solicitud")
        }

        if (requestSent) {
            Text(
                text = "Solicitud enviada correctamente.",
                color = MaterialTheme.colorScheme.primary
            )
        }

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Volver")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContactSalesScreenPreview() {
    Ev2_Master_MartiniTheme {
        ContactSalesScreen(
            onBack = {}
        )
    }
}
