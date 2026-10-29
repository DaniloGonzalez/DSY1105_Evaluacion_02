package com.example.ev2_master_martini.ui.screens

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ev2_master_martini.ui.theme.Ev2_Master_MartiniTheme

@Composable
fun RegisterScreen(onContinue: () -> Unit){
    var name by rememberSaveable {mutableStateOf("") }
    var email by rememberSaveable {mutableStateOf("") }
    var phone by rememberSaveable {mutableStateOf("") }
    var address by rememberSaveable {mutableStateOf("") }
    var rut by rememberSaveable {mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)

    ){
        Text(
            text = "Registro",
            style = MaterialTheme.typography.headlineMedium

        )
        Text(
            text = "Completa tus datos para una mejor experiencia"

        )
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            label = {Text("Correo electronico")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = {phone = it},
            label = {Text("Telefono")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = address,
            onValueChange = {address = it},
            label = {Text("Direccion")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = rut,
            onValueChange = {rut = it},
            label = {Text("RUT")},
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth()

        ) {
            Text("Continuar")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
        Ev2_Master_MartiniTheme {RegisterScreen(onContinue = {})
    }
}

