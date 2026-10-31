package com.example.ev2_master_martini.ui.screens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(
        label = "splash_loader_transition"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200),
            repeatMode = RepeatMode.Restart
        ),
        label = "splash_loader_rotation"
    )

    LaunchedEffect(Unit) {
        delay(2000)
        onFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo corporativo de marca
        Image(
            painter = painterResource(
                id = com.example.ev2_master_martini.R.drawable.logo_master_martini
            ),
            contentDescription = "Logotipo Master Martini",
            modifier = Modifier.size(96.dp)
        )

        Text(
            text = "Master Martini",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        CircularProgressIndicator(
            modifier = Modifier
                .padding(top = 24.dp)
                .graphicsLayer {
                    rotationZ = rotation
                }
        )

        Text(
            text = "Cargando...",
            modifier = Modifier.padding(top = 12.dp)
        )

        Text(
            text = "Contenido para negocio gastronómico",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}