package com.example.ev2_master_martini.data
import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ev2_master_martini.model.UserAccount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userDataStore by preferencesDataStore(
    name = "user_preferences"
)

class UserRepository( private val context: Context){

    private object Keys{
        val name = stringPreferencesKey("user_name")
        val email = stringPreferencesKey("user_email")
        val password = stringPreferencesKey("user_password")
        val phone = stringPreferencesKey("user_phone")
        val address = stringPreferencesKey("user_address")
        val rut = stringPreferencesKey("user_rut")
    }

    suspend fun saveAccount(account: UserAccount){
        context.userDataStore.edit { preferences ->
            preferences[Keys.name] = account.name
            preferences[Keys.email] = account.email
            preferences[Keys.password] = account.password
            preferences[Keys.phone] = account.phone
            preferences[Keys.address] = account.address
            preferences[Keys.rut] = account.rut
        }
    }

    fun getAccount(): Flow<UserAccount?>{
        return context.userDataStore.data.map {
            preferences -> createAccountOrNull(preferences)
        }
    }

    private fun createAccountOrNull(preferences: Preferences) : UserAccount? {
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