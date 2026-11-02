package com.example.ev2_master_martini.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.ev2_master_martini.model.ProfileUiState
import com.example.ev2_master_martini.navigation.AppRoute
import com.example.ev2_master_martini.ui.components.AppBottomBar
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onBack: () -> Unit,
    onHomeClick: () -> Unit,
    onCatalogClick: () -> Unit,
    onEventsClick: () -> Unit,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onRutChange: (String) -> Unit,
    onBusinessTypeChange: (String) -> Unit,
    onBusinessSizeChange: (String) -> Unit,
    onCatalogOptionToggle: (String) -> Unit,
    onSaveAccount: () -> Unit,
    onSaveInterests: () -> Unit,
    onLargeTextChange: (Boolean) -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    var profilePhoto by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        profilePhoto = bitmap
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            cameraLauncher.launch(null)
        }
    }

    if (state.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Mi perfil",
                onBack = onBack
            )
        },
        bottomBar = {
            AppBottomBar(
                selectedRoute = AppRoute.Profile.route,
                onHomeClick = onHomeClick,
                onCatalogClick = onCatalogClick,
                onEventsClick = onEventsClick,
                onProfileClick = {}
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
            Text(
                text = "Mi perfil",
                style = MaterialTheme.typography.headlineMedium
            )

            profilePhoto?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Foto de perfil tomada con la cámara",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Button(
                onClick = {
                    val permissionState = ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.CAMERA
                    )

                    if (permissionState == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(null)
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tomar foto de perfil")
            }

            // ---- Datos personales editables ----
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Datos personales",
                        style = MaterialTheme.typography.titleLarge
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
                    OutlinedTextField(
                        value = state.phone,
                        onValueChange = onPhoneChange,
                        label = { Text("Teléfono") },
                        isError = state.phoneError != null,
                        supportingText = { state.phoneError?.let { Text(it) } },
                        modifier = Modifier.fillMaxWidth()
                    )
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

                    Button(
                        onClick = onSaveAccount,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar datos")
                    }
                }
            }

            // ---- Intereses editables ----
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Mis intereses",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(text = "Tipo de negocio", style = MaterialTheme.typography.titleMedium)
                    listOf("Pastelería", "Panadería", "Restaurante").forEach { option ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.businessType == option,
                                onClick = { onBusinessTypeChange(option) }
                            )
                            Text(option)
                        }
                    }

                    Text(text = "Tamaño del negocio", style = MaterialTheme.typography.titleMedium)
                    listOf("Pequeño", "Mediano", "Grande").forEach { option ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = state.businessSize == option,
                                onClick = { onBusinessSizeChange(option) }
                            )
                            Text(option)
                        }
                    }

                    Text(text = "Preferencias de catálogo", style = MaterialTheme.typography.titleMedium)
                    listOf(
                        "Recetas", "Videos técnicos", "Cursos", "Chocolatería", "Pastelería"
                    ).forEach { option ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = option in state.catalogOptions,
                                onCheckedChange = { onCatalogOptionToggle(option) }
                            )
                            Text(option)
                        }
                    }

                    Button(
                        onClick = onSaveInterests,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar intereses")
                    }
                }
            }

            // ---- Accesibilidad: modo texto grande ----
            Card(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Modo texto grande",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Amplía la tipografía de toda la aplicación para una lectura más cómoda.",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Switch(
                        checked = state.largeTextEnabled,
                        onCheckedChange = onLargeTextChange
                    )
                }
            }

            // Confirmación al guardar (con animación)
            AnimatedVisibility(
                visible = state.saveMessage != null,
                enter = fadeIn()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = state.saveMessage.orEmpty(),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            // CAMBIO REALIZADO: este botón navega realmente al inicio (onHomeClick)
            // en lugar de solo hacer popBackStack.
            Button(
                onClick = onHomeClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al inicio")
            }

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    Ev2_Master_MartiniTheme {
        ProfileScreen(
            state = ProfileUiState(isLoading = false),
            onBack = {},
            onHomeClick = {},
            onCatalogClick = {},
            onEventsClick = {},
            onNameChange = {},
            onEmailChange = {},
            onPhoneChange = {},
            onAddressChange = {},
            onRutChange = {},
            onBusinessTypeChange = {},
            onBusinessSizeChange = {},
            onCatalogOptionToggle = {},
            onSaveAccount = {},
            onSaveInterests = {},
            onLargeTextChange = {},
            onLogout = {}
        )
    }
}