package com.example.ev2_master_martini.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme
import com.example.ev2_master_martini.util.NativeActions

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * Las acciones sobre recursos nativos (marcar y enviar correo) se delegan a
 * [NativeActions]; la pantalla ya no construye Intent ni Uri.
 */
@Composable
fun ContactSalesScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var requestSent by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Contactar ventas",
                onBack = onBack
            )
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
                text = "Solicita orientación sobre productos, cursos o recetas."
            )

            Button(
                onClick = {
                    requestSent = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar solicitud")
            }

            Button(
                onClick = {
                    NativeActions.dial(context, "+56912345678")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Llamar a ventas")
            }

            Button(
                onClick = {
                    NativeActions.sendEmail(context, "ventas@mastermartini.cl")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar correo")
            }

            // Animaciones funcionales: el mensaje de éxito se muestra con una
            // animación de aparición (fade + scale + expand).
            AnimatedVisibility(
                visible = requestSent,
                enter = fadeIn() + scaleIn() + expandVertically()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Solicitud enviada correctamente.",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
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
