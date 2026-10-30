package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.model.InterestsUiState
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun InterestsScreen(
    state: InterestsUiState,
    onBusinessTypeSelected: (String) -> Unit,
    onBusinessSizeSelected: (String) -> Unit,
    onCatalogOptionToggle: (String) -> Unit,
    onContinue: () -> Unit
) {
    val catalogOptions = listOf(
        "Recetas",
        "Videos técnicos",
        "Cursos",
        "Chocolatería",
        "Pastelería"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Cuestionario de intereses",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Selecciona tus preferencias para personalizar el catálogo."
        )

        Text(text = "Tipo de negocio")

        listOf("Pastelería", "Panadería", "Restaurante").forEach { option ->
            RadioOption(
                text = option,
                selected = state.businessType == option,
                onSelected = { onBusinessTypeSelected(option) }
            )
        }

        Text(text = "Tamaño del negocio")

        listOf("Pequeño", "Mediano", "Grande").forEach { option ->
            RadioOption(
                text = option,
                selected = state.businessSize == option,
                onSelected = { onBusinessSizeSelected(option) }
            )
        }

        Text(text = "Opciones del catálogo")

        catalogOptions.forEach { option ->
            CheckboxOption(
                text = option,
                checked = option in state.selectedCatalogOptions,
                onCheckedChange = { onCatalogOptionToggle(option) }
            )
        }

        state.catalogError?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error
            )
        }

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ir al inicio")
        }
    }
}

@Composable
private fun RadioOption(
    text: String,
    selected: Boolean,
    onSelected: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelected
        )

        Text(text = text)
    }
}

@Composable
private fun CheckboxOption(
    text: String,
    checked: Boolean,
    onCheckedChange: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange() }
        )

        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun InterestsScreenPreview() {
    Ev2_Master_MartiniTheme {
        InterestsScreen(
            state = InterestsUiState(),
            onBusinessTypeSelected = {},
            onBusinessSizeSelected = {},
            onCatalogOptionToggle = {},
            onContinue = {}
        )
    }
}
