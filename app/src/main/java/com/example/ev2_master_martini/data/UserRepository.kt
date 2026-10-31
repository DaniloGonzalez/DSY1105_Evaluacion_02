package com.example.ev2_master_martini.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ev2_master_martini.model.UserAccount
import com.example.ev2_master_martini.model.UserInterests
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userDataStore by preferencesDataStore(
    name = "user_preferences"
)

class UserRepository(
    private val context: Context
) {
    private object Keys {
        val name = stringPreferencesKey("user_name")
        val email = stringPreferencesKey("user_email")
        val password = stringPreferencesKey("user_password")
        val phone = stringPreferencesKey("user_phone")
        val address = stringPreferencesKey("user_address")
        val rut = stringPreferencesKey("user_rut")
        val businessType = stringPreferencesKey("business_type")
        val businessSize = stringPreferencesKey("business_size")
        val catalogOptions = stringPreferencesKey("catalog_options")
        val sessionActive = booleanPreferencesKey("session_active")

        val largeText = booleanPreferencesKey("large_text")
    }

    suspend fun saveAccount(account: UserAccount) {
        context.userDataStore.edit { preferences ->
            preferences[Keys.name] = account.name
            preferences[Keys.email] = account.email
            preferences[Keys.password] = account.password
            preferences[Keys.phone] = account.phone
            preferences[Keys.address] = account.address
            preferences[Keys.rut] = account.rut
        }
    }

    /**
     * CAMBIO REALIZADO (perfil editable): actualiza únicamente los campos de
     * contacto (sin tocar la contraseña almacenada). Se usa cuando el usuario
     * edita "Mi perfil".
     */
    suspend fun updateAccount(account: UserAccount) {
        context.userDataStore.edit { preferences ->
            preferences[Keys.name] = account.name
            preferences[Keys.email] = account.email
            preferences[Keys.phone] = account.phone
            preferences[Keys.address] = account.address
            preferences[Keys.rut] = account.rut
        }
    }

    fun getAccount(): Flow<UserAccount?> {
        return context.userDataStore.data.map { preferences ->
            createAccountOrNull(preferences)
        }
    }

    fun isSessionActive(): Flow<Boolean> {
        return context.userDataStore.data.map { preferences ->
            preferences[Keys.sessionActive] ?: false
        }
    }

    suspend fun setSessionActive(active: Boolean) {
        context.userDataStore.edit { preferences ->
            preferences[Keys.sessionActive] = active
        }
    }

    suspend fun logout() {
        setSessionActive(false)
    }

    suspend fun saveInterests(interests: UserInterests) {
        context.userDataStore.edit { preferences ->
            preferences[Keys.businessType] = interests.businessType
            preferences[Keys.businessSize] = interests.businessSize
            preferences[Keys.catalogOptions] = interests.catalogOptions.joinToString("|||")
        }
    }

    /**
     * CAMBIO REALIZADO (perfil editable): actualiza el tipo de negocio, tamaño
     * y preferencias de catálogo del usuario.
     */
    suspend fun updateInterests(interests: UserInterests) {
        context.userDataStore.edit { preferences ->
            preferences[Keys.businessType] = interests.businessType
            preferences[Keys.businessSize] = interests.businessSize
            preferences[Keys.catalogOptions] = interests.catalogOptions.joinToString("|||")
        }
    }

    fun getInterests(): Flow<UserInterests?> {
        return context.userDataStore.data.map { preferences ->
            val options = preferences[Keys.catalogOptions]
                ?.split("|||")
                ?.filter { it.isNotBlank() }
                ?.toSet()
                ?: return@map null

            UserInterests(
                businessType = preferences[Keys.businessType].orEmpty(),
                businessSize = preferences[Keys.businessSize].orEmpty(),
                catalogOptions = options
            )
        }
    }

    // ---- Accesibilidad: modo de texto grande ----

    /** Flujo que indica si el usuario activó el modo de texto grande. */
    fun isLargeText(): Flow<Boolean> {
        return context.userDataStore.data.map { preferences ->
            preferences[Keys.largeText] ?: false
        }
    }

    /** Persiste la preferencia del modo de texto grande. */
    suspend fun setLargeText(enabled: Boolean) {
        context.userDataStore.edit { preferences ->
            preferences[Keys.largeText] = enabled
        }
    }

    private fun createAccountOrNull(
        preferences: Preferences
    ): UserAccount? {
        val name = preferences[Keys.name] ?: return null
        val email = preferences[Keys.email] ?: return null
        val password = preferences[Keys.password] ?: return null
        val phone = preferences[Keys.phone] ?: return null
        val address = preferences[Keys.address] ?: return null
        val rut = preferences[Keys.rut] ?: return null

        return UserAccount(
            name = name,
            email = email,
            password = password,
            phone = phone,
            address = address,
            rut = rut
        )
    }
}