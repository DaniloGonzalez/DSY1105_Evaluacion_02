package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.R
import com.example.ev2_master_martini.model.Recipe
import com.example.ev2_master_martini.navigation.AppRoute
import com.example.ev2_master_martini.ui.components.AppBottomBar
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

/**
 * CAMBIO REALIZADO:
 * 1. (separación de responsabilidades) las recetas llegan desde
 *    RecipesViewModel; la pantalla no las conoce.
 * 2. (Mejora 1) se elimina el botón "Volver" inferior redundante: el retroceso
 *    queda solo en la barra superior.
 */
@Composable
fun RecipesScreen(
    recipes: List<Recipe>,
    onBack: () -> Unit,
    onHomeClick: () -> Unit,
    onCatalogClick: () -> Unit,
    onEventsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onRecipeClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Recetas",
                onBack = onBack
            )
        },
        bottomBar = {
            AppBottomBar(
                selectedRoute = AppRoute.Recipes.route,
                onHomeClick = onHomeClick,
                onCatalogClick = onCatalogClick,
                onEventsClick = onEventsClick,
                onProfileClick = onProfileClick
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
                text = "Recetas para tu negocio",
                style = MaterialTheme.typography.headlineMedium
            )

            Text("Encuentra ideas y preparaciones usando productos Master Martini.")

            recipes.forEach { recipe ->
                RecipeCard(
                    recipe = recipe,
                    onClick = { onRecipeClick(recipe.id) }
                )
            }
        }
    }
}

@Composable
private fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.miniatura_receta),
                contentDescription = "Miniatura de la receta ${recipe.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(width = 120.dp, height = 72.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = recipe.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Ver receta",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview() {
    Ev2_Master_MartiniTheme {
        RecipesScreen(
            recipes = listOf(
                Recipe(
                    id = "torta_chocolate",
                    title = "Torta de chocolate",
                    description = "Receta húmeda y fácil de preparar para pastelería.",
                    ingredients = emptyList(),
                    preparation = "",
                    products = emptyList()
                )
            ),
            onBack = {},
            onHomeClick = {},
            onCatalogClick = {},
            onEventsClick = {},
            onProfileClick = {},
            onRecipeClick = {}
        )
    }
}
