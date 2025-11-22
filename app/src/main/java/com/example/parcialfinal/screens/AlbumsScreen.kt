package com.example.parcialfinal.screens


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcialfinal.data.Album
import com.example.parcialfinal.data.AlbumsApi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsScreen(onBack: () -> Unit) {
    var albums by remember { mutableStateOf<List<Album>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }
    val api = AlbumsApi.create()

    LaunchedEffect(Unit) {
        try {
            albums = api.getAlbums()
        } catch (e: Exception) {
            error = e.localizedMessage
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("Álbumes") })
        if (error != null) {
            Text(error ?: "Error", color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(16.dp))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                items(albums) { album ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(6.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(album.title, style = MaterialTheme.typography.titleMedium)
                            Spacer(Modifier.height(4.dp))
                            Text("Album ID: ${album.id}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth().padding(8.dp)) { Text("Volver") }
    }
}
