package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme
import androidx.compose.material3.Text

@Composable
fun InterestsScreen(onContinue: () -> Unit){
    var businessType by rememberSaveable { mutableStateOf("") }
    var businessSize by rememberSaveable { mutableStateOf("") }
    var contentPreference by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Cuestionario de intereses"
        )

        Text(
            text = "Cuéntanos un poco sobre tu negocio."
        )

        Text(text = "Tipo de negocio")

        InterestOption(
            text = "Pastelería",
            selected = businessType == "Pastelería",
            onSelected = { businessType = "Pastelería" }
        )

        InterestOption(
            text = "Panadería",
            selected = businessType == "Panadería",
            onSelected = { businessType = "Panadería" }
        )

        InterestOption(
            text = "Restaurante",
            selected = businessType == "Restaurante",
            onSelected = { businessType = "Restaurante" }
        )

        Text(text = "Tamaño del negocio")

        InterestOption(
            text = "Pequeño",
            selected = businessSize == "Pequeño",
            onSelected = { businessSize = "Pequeño" }
        )

        InterestOption(
            text = "Mediano",
            selected = businessSize == "Mediano",
            onSelected = { businessSize = "Mediano" }
        )

        InterestOption(
            text = "Grande",
            selected = businessSize == "Grande",
            onSelected = { businessSize = "Grande" }
        )

        Text(text = "Contenido de interés")

        InterestOption(
            text = "Recetas",
            selected = contentPreference == "Recetas",
            onSelected = { contentPreference = "Recetas" }
        )

        InterestOption(
            text = "Videos técnicos",
            selected = contentPreference == "Videos técnicos",
            onSelected = { contentPreference = "Videos técnicos" }
        )

        InterestOption(
            text = "Cursos",
            selected = contentPreference == "Cursos",
            onSelected = { contentPreference = "Cursos" }
        )

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ir al inicio")
        }
    }
}


@Composable
private fun InterestOption(text: String, selected: Boolean, onSelected: ()-> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelected
        )
        Text(text=text)
    }
}

@Preview(showBackground = true)
@Composable
fun InterestsScreenPreview() {
    Ev2_Master_MartiniTheme {
        InterestsScreen(
            onContinue = {}
        )
    }
}