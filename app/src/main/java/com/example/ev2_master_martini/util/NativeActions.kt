package com.example.ev2_master_martini.util

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat

/**
 * CAMBIO REALIZADO (separación de responsabilidades):
 * Aísla el acceso a recursos nativos del dispositivo (marcado telefónico,
 * correo y permiso de cámara). Antes, los Intent y la verificación de permisos
 * estaban dentro de los composables; ahora la UI solo invoca estas funciones.
 */
object NativeActions {

    /** Abre el marcador telefónico con el número indicado. */
    fun dial(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        context.startActivity(intent)
    }

    /** Abre el cliente de correo para escribir al destinatario indicado. */
    fun sendEmail(context: Context, emailAddress: String) {
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$emailAddress"))
        context.startActivity(intent)
    }

    /** Indica si la aplicación ya cuenta con el permiso de cámara. */
    fun hasCameraPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
}
