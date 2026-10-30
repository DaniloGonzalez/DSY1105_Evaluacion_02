package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun RecipesScreen(
    onBack: () -> Unit,
    onHomeClick: () -> Unit,
    onCatalogClick: () -> Unit,
    onProfileClick: () -> Unit,
    onRecipeClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "Recetas",
                    onBack = onBack
                )
                AppBottomBar(
                    selectedRoute = AppRoute.Recipes.route,
                    onHomeClick = onHomeClick,
                    onCatalogClick = onCatalogClick,
                    onProfileClick = onProfileClick
                )
            }
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

            RecipeCard(
                title = "Torta de chocolate",
                description = "Receta húmeda y fácil de preparar para pastelería.",
                onClick = { onRecipeClick("torta_chocolate") }
            )

            RecipeCard(
                title = "Brownie clásico",
                description = "Brownie de chocolate con textura suave y sabor intenso.",
                onClick = { onRecipeClick("brownie_clasico") }
            )

            RecipeCard(
                title = "Mousse de chocolate",
                description = "Postre ligero ideal para presentaciones individuales.",
                onClick = { onRecipeClick("mousse_chocolate") }
            )

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }
        }
    }
}

@Composable
private fun RecipeCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(description)
            Text(
                text = "Ver receta",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview() {
    Ev2_Master_MartiniTheme {
        RecipesScreen(
            onBack = {},
            onHomeClick = {},
            onCatalogClick = {},
            onProfileClick = {},
            onRecipeClick = {}
        )
    }
}
