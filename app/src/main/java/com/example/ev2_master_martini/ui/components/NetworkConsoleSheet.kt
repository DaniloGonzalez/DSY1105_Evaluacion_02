package com.example.ev2_master_martini.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.data.remote.NetworkCall
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val THEMEALDB_PREFIX = "https://www.themealdb.com/"
private val OkGreen = Color(0xFF2E7D32)

/** Endpoint legible: quita el dominio para mostrar solo la ruta + query. */
fun NetworkCall.shortEndpoint(): String = url.removePrefix(THEMEALDB_PREFIX)

/** Etiqueta de estado: "200 OK" para códigos exitosos, o el código tal cual. */
fun NetworkCall.statusLabel(): String =
    if (code in 200..299) "$code OK" else code.toString()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkConsoleSheet(
    visible: Boolean,
    calls: List<NetworkCall>,
    onClear: () -> Unit,
    onDismiss: () -> Unit
) {
    if (!visible) return

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Consola de red",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onClear) {
                    Text("Limpiar")
                }
            }

            Text(
                text = "Evidencia de las llamadas a la API externa (TheMealDB).",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (calls.isEmpty()) {
                Text("Aún no hay llamadas registradas.")
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(calls) { call ->
                        NetworkCallRow(call)
                    }
                }
            }
        }
    }
}

@Composable
private fun NetworkCallRow(call: NetworkCall) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${call.method} ${call.shortEndpoint()}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = formatTime(call.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = call.statusLabel(),
                style = MaterialTheme.typography.labelLarge,
                color = if (call.code in 200..299) OkGreen else MaterialTheme.colorScheme.error
            )
            Text(
                text = "${call.durationMs} ms",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun formatTime(timestamp: Long): String =
    SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(timestamp))
