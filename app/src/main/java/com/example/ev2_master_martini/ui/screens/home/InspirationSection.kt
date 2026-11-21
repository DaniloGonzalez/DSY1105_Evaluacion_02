package com.example.ev2_master_martini.ui.screens.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ev2_master_martini.data.remote.NetworkCall
import com.example.ev2_master_martini.model.InspirationRecipe
import com.example.ev2_master_martini.model.InspirationUiState
import com.example.ev2_master_martini.ui.components.shortEndpoint
import com.example.ev2_master_martini.ui.components.statusLabel
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

/**
 * CAMBIO REALIZADO (integración de API externa - TheMealDB):
 * Sección de Home "Inspírate con recetas del mundo". Incluye buscador
 * (search.php), chips de categoría (categories.php + filter.php), botón
 * "Sorpréndeme" (random.php), carrusel de resultados con imágenes remotas
 * (Coil) y un chip de estado que muestra el último endpoint con su 200 OK.
 *
 * Es un componente de presentación: no conoce la red, solo recibe estado y
 * emite eventos hacia InspirationViewModel.
 */
@Composable
fun InspirationSection(
    state: InspirationUiState,
    lastCall: NetworkCall?,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onCategorySelected: (String) -> Unit,
    onSurprise: () -> Unit,
    onRetry: () -> Unit,
    onRecipeClick: (String) -> Unit,
    onOpenConsole: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Inspírate con recetas del mundo",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = onSurprise) {
                Icon(
                    imageVector = Icons.Filled.Shuffle,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Sorpréndeme")
            }
        }

        // Chip de estado: último endpoint consultado + código + tiempo.
        lastCall?.let { call ->
            SuggestionChip(
                onClick = onOpenConsole,
                label = {
                    Text(
                        "${call.method} ${call.shortEndpoint()} · " +
                            "${call.statusLabel()} · ${call.durationMs} ms"
                    )
                }
            )
        }

        OutlinedTextField(
            value = state.query,
            onValueChange = onQueryChange,
            label = { Text("Buscar receta") },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = onSearch) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Buscar"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (state.categories.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                state.categories.forEach { category ->
                    FilterChip(
                        selected = state.selectedCategory == category,
                        onClick = { onCategorySelected(category) },
                        label = { Text(category) }
                    )
                }
            }
        }

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.errorMessage != null -> {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = state.errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = onRetry) {
                        Text("Reintentar")
                    }
                }
            }

            state.recipes.isEmpty() -> {
                Text(
                    text = "No se encontraron recetas. Prueba otra búsqueda.",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            else -> {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.recipes, key = { it.id }) { recipe ->
                        InspirationCard(
                            recipe = recipe,
                            onClick = { onRecipeClick(recipe.id) }
                        )
                    }
                }
            }
        }

        TextButton(onClick = onOpenConsole) {
            Text("Ver consola de red")
        }
    }
}

@Composable
private fun InspirationCard(
    recipe: InspirationRecipe,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.width(160.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column {
            AsyncImage(
                model = recipe.thumbnailUrl,
                contentDescription = recipe.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = listOfNotNull(recipe.area, recipe.category).joinToString(" · "),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InspirationSectionPreview() {
    Ev2_Master_MartiniTheme {
        InspirationSection(
            state = InspirationUiState(
                isLoading = false,
                query = "chocolate",
                categories = listOf("Beef", "Chicken", "Dessert"),
                recipes = listOf(
                    InspirationRecipe(
                        id = "52776",
                        name = "Chocolate Gateau",
                        category = "Dessert",
                        area = "France",
                        thumbnailUrl = null
                    )
                )
            ),
            lastCall = null,
            onQueryChange = {},
            onSearch = {},
            onCategorySelected = {},
            onSurprise = {},
            onRetry = {},
            onRecipeClick = {},
            onOpenConsole = {}
        )
    }
}
