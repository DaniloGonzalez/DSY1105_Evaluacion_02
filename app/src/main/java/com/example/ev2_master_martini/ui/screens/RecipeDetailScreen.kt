package com.example.ev2_master_martini.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.model.Recipe
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme
import com.example.ev2_master_martini.ui.theme.MartiniGold

/**
 * CAMBIO REALIZADO:
 * 1. (separación de responsabilidades) la receta llega como modelo desde
 *    RecipesViewModel; la pantalla no la resuelve ni la conoce.
 * 2. (Mejora 1) se elimina el botón "Volver a recetas" inferior: el retroceso
 *    queda solo en la barra superior.
 */
@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBack: () -> Unit,
    onContactSales: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Detalle de receta",
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
                text = recipe.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = recipe.description,
                style = MaterialTheme.typography.bodyLarge
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Ingredientes",
                        style = MaterialTheme.typography.titleLarge
                    )
                    recipe.ingredients.forEach { ingredient ->
                        Text("• $ingredient")
                    }
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Preparación",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(recipe.preparation)
                }
            }

            // Sub-sección de productos de marca (insumos).
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = MartiniGold
                        )
                        Text(
                            text = "Productos Master Martini Utilizados",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    recipe.products.forEach { product ->
                        Text("• $product")
                    }
                }
            }

            AnimatedVisibility(visible = true, enter = fadeIn()) {
                Button(
                    onClick = onContactSales,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Solicitar asesoría")
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailScreenPreview() {
    Ev2_Master_MartiniTheme {
        RecipeDetailScreen(
            recipe = Recipe(
                id = "torta_chocolate",
                title = "Torta de chocolate",
                description = "Receta húmeda y fácil de preparar para pastelería.",
                ingredients = listOf("200 g de cobertura de chocolate"),
                preparation = "Precalienta el horno a 180 °C.",
                products = listOf("Cobertura de chocolate semiamargo")
            ),
            onBack = {},
            onContactSales = {}
        )
    }
}
