package com.example.parcialfinal.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
fun LocationScreen(onBack: () -> Unit) {

    val ctx = LocalContext.current
    var latLng by remember { mutableStateOf<Pair<Double, Double>?>(null) }
    var message by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        val fine = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarse = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (!fine && !coarse) {
            message = "Permisos de ubicación denegados"
        }
    }

    LaunchedEffect(Unit) {

        val fineGranted = ContextCompat.checkSelfPermission(
            ctx, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted = ContextCompat.checkSelfPermission(
            ctx, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!fineGranted && !coarseGranted) {
            launcher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
            return@LaunchedEffect
        }

        try {
            val fClient = LocationServices.getFusedLocationProviderClient(ctx)

            // 🔍 Primero intentamos obtener la última ubicación guardada
            val lastLoc = fClient.lastLocation.await()

            if (lastLoc != null) {
                latLng = lastLoc.latitude to lastLoc.longitude
                return@LaunchedEffect
            }

            // ❗ Si lastLocation es null → pedimos una ubicación nueva
            val current = fClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).await()

            if (current != null) {
                latLng = current.latitude to current.longitude
            } else {
                message = "No se obtuvo ubicación. Activa el GPS o prueba en un dispositivo real."
            }

        } catch (e: Exception) {
            message = e.localizedMessage
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TopAppBar(title = { Text("Ubicación GPS") })
        Spacer(Modifier.height(12.dp))

        latLng?.let { (lat, lon) ->
            Text("Latitud: $lat")
            Spacer(Modifier.height(8.dp))
            Text("Longitud: $lon")
        } ?: run {
            message?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            } ?: Text("Obteniendo ubicación...")
        }

        Spacer(Modifier.height(24.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Volver")
        }
    }
}
