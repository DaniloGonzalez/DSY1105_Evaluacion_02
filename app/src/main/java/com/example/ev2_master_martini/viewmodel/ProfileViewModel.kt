package com.example.ev2_master_martini.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ev2_master_martini.data.UserRepository
import com.example.ev2_master_martini.model.ProfileUiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application)

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            userRepository.logout()
            onComplete()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val account = userRepository.getAccount().first()
            val interests = userRepository.getInterests().first()

            _uiState.value = ProfileUiState(
                isLoading = false,
                name = account?.name.orEmpty(),
                email = account?.email.orEmpty(),
                phone = account?.phone.orEmpty(),
                address = account?.address.orEmpty(),
                rut = account?.rut.orEmpty(),
                businessType = interests?.businessType.orEmpty(),
                businessSize = interests?.businessSize.orEmpty(),
                catalogOptions = interests?.catalogOptions.orEmpty()
            )
        }
    }
}
