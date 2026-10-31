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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme
import com.example.ev2_master_martini.ui.theme.MartiniGold

@Composable
fun RecipeDetailScreen(
    recipeId: String,
    onBack: () -> Unit,
    onContactSales: () -> Unit
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
            preparation = "Derrite el chocolate junto con la mantequilla. Incorpora el azúcar y los huevos. Agrega la harina y las nueces. Vierte la mezcla en un molde y hornea durante 25 minutos a 180 °C.",
            products = listOf(
                "Cobertura de chocolate premium 70%",
                "Harina de fuerza para repostería",
                "Mantequilla sin sal certificada"
            )
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
            preparation = "Derrite el chocolate y déjalo entibiar. Bate la crema y las claras por separado. Integra todo con movimientos envolventes y refrigera durante al menos 2 horas.",
            products = listOf(
                "Chocolate de cobertura 60%",
                "Crema de repostería 40% M.G.",
                "Azúcar flor impalpable"
            )
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
            preparation = "Precalienta el horno a 180 °C. Mezcla los ingredientes secos. Incorpora los huevos, la leche y la mantequilla. Agrega el chocolate derretido, vierte en un molde y hornea durante 40 minutos.",
            products = listOf(
                "Cobertura de chocolate semiamargo",
                "Harina 0000 para pastelería",
                "Polvo de hornear profesional",
                "Esencia de vainilla"
            )
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

            // CAMBIO REALIZADO: sub-sección de productos de marca (insumos).
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
    val preparation: String,
    val products: List<String>
)

@Preview(showBackground = true)
@Composable
fun RecipeDetailScreenPreview() {
    Ev2_Master_MartiniTheme {
        RecipeDetailScreen(
            recipeId = "torta_chocolate",
            onBack = {},
            onContactSales = {}
        )
    }
}