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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun HomeScreen(onCatalogClick: () ->Unit, onEventsClick: ()-> Unit, onProfileClick:()-> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Bienvenido a Master Martini",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Encuentra contenidos y oportunidades para tu negocio gastronomico"
        )
        Button(
            onClick = onCatalogClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Ver Catalogo"
            )
        }
        Button(
            onClick =  onEventsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Ver Eventos y Novedades"
            )
        }
        Button(
            onClick = onProfileClick,
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(
                text = "Mi Perfil"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    Ev2_Master_MartiniTheme{
        HomeScreen(
            onCatalogClick = {},
            onEventsClick = {},
            onProfileClick = {}
        )
    }
}