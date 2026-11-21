package com.example.ev2_master_martini.ui.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.model.ProfileUiState
import com.example.ev2_master_martini.navigation.AppRoute
import com.example.ev2_master_martini.ui.components.AppBottomBar
import com.example.ev2_master_martini.ui.components.AppTopBar
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme
import com.example.ev2_master_martini.util.NativeActions

/**
 * CAMBIOS REALIZADOS:
 * 1. (separación de responsabilidades) la verificación del permiso de cámara
 *    se delega a [NativeActions].
 * 2. (Mejora 1) se elimina el botón "Volver al inicio" inferior redundante: el
 *    retroceso queda en la barra superior y el acceso a Inicio en la barra
 *    inferior.
 */
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
                // CAMBIO REALIZADO (foto de perfil):
                // La foto se muestra en un recuadro fijo (avatar circular de
                // 160 dp) con ContentScale.Crop, en lugar de una Image sin alto
                // ni escala que quedaba centrada y muy pequeña.
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Foto de perfil tomada con la cámara",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(160.dp)
                        .clip(CircleShape)
                )
            }

            Button(
                onClick = {
                    if (NativeActions.hasCameraPermission(context)) {
                        cameraLauncher.launch(null)
                    } else {
                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
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
