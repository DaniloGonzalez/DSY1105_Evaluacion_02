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
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun RecipeDetailScreen(
    recipeId: String,
    onBack: () -> Unit
) {
    val recipe = when (recipeId) {
        "brownie_clasico" -> RecipeDetail(
            title = "Brownie clásico",
            description = "Brownie de chocolate con textura suave y sabor intenso.",
            ingredients = listOf(
                "200 g de chocolate",
                "150 g de mantequilla",
                "180 g de azúcar",
                "3 huevos",
                "100 g de harina",
                "100 g de nueces"
            ),
            preparation = "Derrite el chocolate junto con la mantequilla. Incorpora el azúcar y los huevos. Agrega la harina y las nueces. Vierte la mezcla en un molde y hornea durante 25 minutos a 180 °C."
        )
        "mousse_chocolate" -> RecipeDetail(
            title = "Mousse de chocolate",
            description = "Postre ligero ideal para presentaciones individuales.",
            ingredients = listOf(
                "250 g de chocolate",
                "400 ml de crema",
                "50 g de azúcar",
                "3 claras de huevo"
            ),
            preparation = "Derrite el chocolate y déjalo entibiar. Bate la crema y las claras por separado. Integra todo con movimientos envolventes y refrigera durante al menos 2 horas."
        )
        else -> RecipeDetail(
            title = "Torta de chocolate",
            description = "Receta húmeda y fácil de preparar para pastelería.",
            ingredients = listOf(
                "200 g de cobertura de chocolate",
                "250 g de harina",
                "200 g de azúcar",
                "4 huevos",
                "150 ml de leche",
                "100 g de mantequilla"
            ),
            preparation = "Precalienta el horno a 180 °C. Mezcla los ingredientes secos. Incorpora los huevos, la leche y la mantequilla. Agrega el chocolate derretido, vierte en un molde y hornea durante 40 minutos."
        )
    }

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

            Text(recipe.description)

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

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver a recetas")
            }
        }
    }
}

private data class RecipeDetail(
    val title: String,
    val description: String,
    val ingredients: List<String>,
    val preparation: String
)

@Preview(showBackground = true)
@Composable
fun RecipeDetailScreenPreview() {
    Ev2_Master_MartiniTheme {
        RecipeDetailScreen(
            recipeId = "torta_chocolate",
            onBack = {}
        )
    }
}
