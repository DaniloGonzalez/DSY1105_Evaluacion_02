package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.model.RegisterUiState
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun RegisterScreen(
    state: RegisterUiState,
    onBack: () -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneCountryCodeChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onRutChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    var countryMenuExpanded by remember {
        mutableStateOf(false)
    }

    val countries = listOf(
        "+56" to "Chile",
        "+54" to "Argentina",
        "+55" to "Brasil",
        "+34" to "España",
        "+1" to "Estados Unidos"
    )

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Registro",
                onBack = onBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Completa tus datos para una mejor experiencia."
            )

            OutlinedTextField(
                value = state.name,
                onValueChange = onNameChange,
                label = { Text("Nombre") },
                isError = state.nameError != null,
                supportingText = { state.nameError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.email,
                onValueChange = onEmailChange,
                label = { Text("Correo electrónico") },
                isError = state.emailError != null,
                supportingText = { state.emailError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = "Teléfono")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    OutlinedButton(
                        onClick = {
                            countryMenuExpanded = true
                        }
                    ) {
                        Text(state.phoneCountryCode)
                    }

                    DropdownMenu(
                        expanded = countryMenuExpanded,
                        onDismissRequest = {
                            countryMenuExpanded = false
                        }
                    ) {
                        countries.forEach { (code, country) ->
                            DropdownMenuItem(
                                text = { Text("$code - $country") },
                                onClick = {
                                    onPhoneCountryCodeChange(code)
                                    countryMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = state.phoneNumber,
                    onValueChange = onPhoneNumberChange,
                    label = { Text("Número") },
                    isError = state.phoneError != null,
                    supportingText = {
                        state.phoneError?.let { Text(it) }
                    },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = state.address,
                onValueChange = onAddressChange,
                label = { Text("Dirección") },
                isError = state.addressError != null,
                supportingText = { state.addressError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.rut,
                onValueChange = onRutChange,
                label = { Text("RUT de empresa") },
                isError = state.rutError != null,
                supportingText = { state.rutError?.let { Text(it) } },
                modifier = Modifier.fillMaxWidth()
            )

            PasswordField(
                value = state.password,
                onValueChange = onPasswordChange,
                label = "Contraseña",
                error = state.passwordError
            )

            PasswordField(
                value = state.confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = "Confirmar contraseña",
                error = state.confirmPasswordError
            )

            state.saveError?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = onContinue,
                enabled = !state.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Continuar")
                }
            }
        }
    }
}

@Composable
private fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String?
) {
    var visible by remember {
        mutableStateOf(false)
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (visible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            IconButton(onClick = { visible = !visible }) {
                Icon(
                    imageVector = if (visible) {
                        Icons.Default.VisibilityOff
                    } else {
                        Icons.Default.Visibility
                    },
                    contentDescription = if (visible) {
                        "Ocultar contraseña"
                    } else {
                        "Mostrar contraseña"
                    }
                )
            }
        },
        isError = error != null,
        supportingText = {
            error?.let { Text(it) }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    Ev2_Master_MartiniTheme {
        RegisterScreen(
            state = RegisterUiState(),
            onBack = {},
            onNameChange = {},
            onEmailChange = {},
            onPhoneCountryCodeChange = {},
            onPhoneNumberChange = {},
            onAddressChange = {},
            onRutChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onContinue = {}
        )
    }
}
