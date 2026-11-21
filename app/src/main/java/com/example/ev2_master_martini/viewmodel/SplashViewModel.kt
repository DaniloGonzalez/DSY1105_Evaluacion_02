package com.example.ev2_master_martini.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ev2_master_martini.data.UserRepository
import com.example.ev2_master_martini.model.StartDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * La decisión de a dónde entrar (según sesión e intereses) sale del NavHost y
 * queda en este ViewModel, de modo que la navegación no lea la persistencia.
 */
class SplashViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _destination = MutableStateFlow<StartDestination?>(null)
    val destination = _destination.asStateFlow()

    fun resolveStartDestination() {
        if (_destination.value != null) return

        viewModelScope.launch {
            val sessionActive = userRepository.isSessionActive().first()
            val interests = userRepository.getInterests().first()

            _destination.value = when {
                sessionActive && !interests?.catalogOptions.isNullOrEmpty() ->
                    StartDestination.Home
                sessionActive -> StartDestination.Interests
                else -> StartDestination.Login
            }
        }
    }
}
