package com.example.ev2_master_martini.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
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
import com.example.ev2_master_martini.model.CatalogCategory
import com.example.ev2_master_martini.model.CatalogUiState
import com.example.ev2_master_martini.model.ContentItem
import com.example.ev2_master_martini.navigation.AppRoute
import com.example.ev2_master_martini.ui.components.AppBottomBar
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * La pantalla ya no conoce ni filtra los datos. Recibe el estado desde
 * CatalogViewModel (categoría seleccionada + ítems) y solo emite eventos de
 * selección. Los ContentItem y las categorías provienen de la capa de datos.
 */
@Composable
fun CatalogScreen(
    state: CatalogUiState,
    onCategorySelected: (CatalogCategory) -> Unit,
    onBack: () -> Unit,
    onContentClick: () -> Unit,
    onHomeClick: () -> Unit,
    onEventsClick: () -> Unit,
    onRecipesClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = "Catálogo",
                onBack = onBack
            )
        },
        bottomBar = {
            AppBottomBar(
                selectedRoute = AppRoute.Catalog.route,
                onHomeClick = onHomeClick,
                onCatalogClick = {},
                onEventsClick = onEventsClick,
                onProfileClick = onProfileClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Catálogo",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Explora nuestros contenidos técnicos.",
                style = MaterialTheme.typography.bodyLarge
            )

            // Filtros por categoría (FilterChips), desplazables horizontalmente
            // para no desbordar en pantallas pequeñas (accesibilidad).
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CatalogCategory.entries.forEach { category ->
                    FilterChip(
                        selected = state.selectedCategory == category,
                        onClick = {
                            onCategorySelected(category)
                        },
                        label = { Text(category.label) }
                    )
                }
            }

            // Lista de tarjetas con miniatura (animadas al filtrar)
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.items, key = { it.id }) { item ->
                    AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(tween(300)),
                        exit = fadeOut(tween(200))
                    ) {
                        ContentCard(
                            item = item,
                            onClick = {
                                when (item.category) {
                                    CatalogCategory.Recipes -> onRecipesClick()
                                    else -> onContentClick()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ContentCard(
    item: ContentItem,
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
            // Miniatura del contenido
            Image(
                painter = painterResource(id = item.thumbnailRes),
                contentDescription = "Miniatura de ${item.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 120.dp, height = 72.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = item.category.label,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Ver ${item.title}"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatalogScreenPreview() {
    Ev2_Master_MartiniTheme {
        CatalogScreen(
            state = CatalogUiState(),
            onCategorySelected = {},
            onBack = {},
            onContentClick = {},
            onHomeClick = {},
            onEventsClick = {},
            onRecipesClick = {},
            onProfileClick = {}
        )
    }
}
