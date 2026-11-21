package com.example.ev2_master_martini.model

/**
 * Destino de entrada resuelto a partir de la sesión y los intereses del usuario.
 *
 * Se define en la capa de modelo para que los ViewModel no dependan de las
 * rutas de navegación (desacoplamiento entre lógica y navegación).
 */
enum class StartDestination {
    Home,
    Interests,
    Login
}
