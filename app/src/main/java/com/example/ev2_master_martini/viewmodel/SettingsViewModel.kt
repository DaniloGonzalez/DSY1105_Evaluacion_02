package com.example.ev2_master_martini.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ev2_master_martini.data.UserRepository
import kotlinx.coroutines.flow.Flow

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * La preferencia de accesibilidad (texto grande) sale de MainActivity y queda
 * expuesta por este ViewModel, de modo que la Activity no lea DataStore.
 */
class SettingsViewModel(
    userRepository: UserRepository
) : ViewModel() {

    val largeText: Flow<Boolean> = userRepository.isLargeText()
}
