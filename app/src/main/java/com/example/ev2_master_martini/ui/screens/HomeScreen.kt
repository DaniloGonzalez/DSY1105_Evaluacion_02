package com.example.ev2_master_martini.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.model.HomeEvent
import com.example.ev2_master_martini.model.HomeUiState
import com.example.ev2_master_martini.model.RecommendedContent
import com.example.ev2_master_martini.navigation.AppRoute
import com.example.ev2_master_martini.ui.components.AppBottomBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme
import com.example.ev2_master_martini.ui.theme.MartiniWine
import com.example.ev2_master_martini.ui.theme.MartiniCream

@Composable
fun HomeScreen(
    state: HomeUiState,
    onCatalogClick: () -> Unit,
    onEventsClick: () -> Unit,
    onProfileClick: () -> Unit,
    onRecommendationClick: (String) -> Unit,
    onEventClick: (String) -> Unit
) {
    Scaffold(
        bottomBar = {
            AppBottomBar(
                selectedRoute = AppRoute.Home.route,
                onHomeClick = {},
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
            // Saludo personalizado
            Text(
                text = if (state.userName.isNotBlank()) {
                    "¡Hola, ${state.userName}!"
                } else {
                    "¡Hola!"
                },
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Encuentra contenidos y oportunidades para tu negocio gastronómico.",
                style = MaterialTheme.typography.bodyLarge
            )

            // Sección de recomendaciones personalizadas (dashboard)
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 })
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Recomendado para ti",
                        style = MaterialTheme.typography.titleLarge
                    )

                    if (state.businessType.isNotBlank()) {
                        SuggestionChip(
                            onClick = {},
                            label = {
                                Text("Negocio: ${state.businessType}")
                            }
                        )
                    }

                    if (state.preferredCategories.isNotEmpty()) {
                        Text(
                            text = "Según tus intereses: ${state.preferredCategories.joinToString(", ")}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    state.recommendations.forEach { content ->
                        RecommendationCard(
                            content = content,
                            onClick = { onRecommendationClick(content.id) }
                        )
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator()
            }

            // Sección de eventos y novedades (carrusel)
            Text(
                text = "Eventos y novedades",
                style = MaterialTheme.typography.titleLarge
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(state.events) { event ->
                    EventCarouselCard(
                        event = event,
                        onClick = { onEventClick(event.id) }
                    )
                }
            }

            // Accesos directos principales
            Button(
                onClick = onCatalogClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ver catálogo")
            }

            Button(
                onClick = onProfileClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mi perfil")
            }
        }
    }
}

@Composable
private fun RecommendationCard(
    content: RecommendedContent,
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
                painter = painterResource(
                    id = thumbnailResourceId(content.thumbnailRes)
                ),
                contentDescription = "Miniatura de ${content.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(48.dp)
                    .padding(horizontal = 0.dp)
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = content.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = content.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            androidx.compose.material3.Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Ver ${content.title}"
            )
        }
    }
}

@Composable
private fun EventCarouselCard(
    event: HomeEvent,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(0.8f),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MartiniWine
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium,
                color = MartiniCream
            )
            Text(
                text = event.date,
                style = MaterialTheme.typography.bodySmall,
                color = MartiniCream
            )
            Text(
                text = event.availability,
                style = MaterialTheme.typography.labelLarge,
                color = MartiniCream
            )
        }
    }
}

/** Convierte el nombre del drawable (mock data) en el id de recurso. */
private fun thumbnailResourceId(name: String): Int {
    return when (name) {
        "thumb_curso" -> com.example.ev2_master_martini.R.drawable.miniatura_curso
        "thumb_receta" -> com.example.ev2_master_martini.R.drawable.miniatura_receta
        "thumb_video" -> com.example.ev2_master_martini.R.drawable.miniatura_video
        else -> com.example.ev2_master_martini.R.drawable.miniatura_evento
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Ev2_Master_MartiniTheme {
        HomeScreen(
            state = HomeUiState(
                isLoading = false,
                userName = "Ana",
                preferredCategories = setOf("Chocolatería", "Cursos"),
                recommendations = listOf(
                    RecommendedContent("1", "Curso de chocolatería", "Cursos", "thumb_curso")
                ),
                events = listOf(
                    HomeEvent("1", "Taller de chocolatería", "Cupos disponibles", "15 de noviembre")
                )
            ),
            onCatalogClick = {},
            onEventsClick = {},
            onProfileClick = {},
            onRecommendationClick = {},
            onEventClick = {}
        )
    }
}