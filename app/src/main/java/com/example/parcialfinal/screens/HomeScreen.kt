package com.example.parcialfinal.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcialfinal.ui.Screen
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onNavigate: (String) -> Unit) {
    val auth = FirebaseAuth.getInstance()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TopAppBar(title = { Text("Home") })
        Spacer(Modifier.height(12.dp))
        Button(onClick = { onNavigate(Screen.AddSong.route) }, modifier = Modifier.fillMaxWidth()) { Text("Agregar canción") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { onNavigate(Screen.Albums.route) }, modifier = Modifier.fillMaxWidth()) { Text("Ver álbumes") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { onNavigate(Screen.Location.route) }, modifier = Modifier.fillMaxWidth()) { Text("Ver ubicación") }
        Spacer(Modifier.height(20.dp))
        Button(onClick = {
            auth.signOut()
            onNavigate(Screen.Login.route)
        }, modifier = Modifier.fillMaxWidth()) { Text("Cerrar sesión") }
    }
}
