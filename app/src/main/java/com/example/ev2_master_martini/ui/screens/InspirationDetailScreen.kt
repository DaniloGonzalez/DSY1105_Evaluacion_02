package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ev2_master_martini.model.InspirationDetailUiState
import com.example.ev2_master_martini.model.InspirationIngredient
import com.example.ev2_master_martini.model.InspirationRecipe
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme
import com.example.ev2_master_martini.util.NativeActions

@Composable
fun InspirationDetailScreen(
    state: InspirationDetailUiState,
    onBack: () -> Unit,
    onRetry: () -> Unit
) {
    val recipe = state.recipe

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Receta",
                onBack = onBack
            )
        }
    ) { innerPadding ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = onRetry) {
                        Text("Reintentar")
                    }
                }
            }

            recipe == null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp)
                ) {
                    Text("No se encontró la receta.")
                }
            }

            else -> {
                val context = LocalContext.current

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AsyncImage(
                        model = recipe.thumbnailUrl,
                        contentDescription = recipe.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )

                    Text(
                        text = recipe.name,
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        recipe.area?.let { area ->
                            SuggestionChip(onClick = {}, label = { Text(area) })
                        }
                        recipe.category?.let { category ->
                            SuggestionChip(onClick = {}, label = { Text(category) })
                        }
                    }

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
                                Text(
                                    text = "• ${ingredient.measure} ${ingredient.name}".trim()
                                )
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
                            Text(recipe.instructions.orEmpty())
                        }
                    }

                    recipe.youtubeUrl?.let { url ->
                        Button(
                            onClick = { NativeActions.openUrl(context, url) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Ver video en YouTube")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InspirationDetailScreenPreview() {
    Ev2_Master_MartiniTheme {
        InspirationDetailScreen(
            state = InspirationDetailUiState(
                isLoading = false,
                recipe = InspirationRecipe(
                    id = "52776",
                    name = "Chocolate Gateau",
                    category = "Dessert",
                    area = "France",
                    thumbnailUrl = null,
                    instructions = "Preheat the oven to 180°C...",
                    ingredients = listOf(
                        InspirationIngredient("Chocolate", "250g"),
                        InspirationIngredient("Butter", "175g")
                    )
                )
            ),
            onBack = {},
            onRetry = {}
        )
    }
}
