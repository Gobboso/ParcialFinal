package com.example.parcialfinal.screens

import androidx.compose.foundation.layout.*
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parcialfinal.data.Song
import com.example.parcialfinal.data.SongRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSongScreen(onBack: () -> Unit) {
    val repo = SongRepository()
    var author by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var album by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TopAppBar(title = { Text("Agregar canción") })
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = author, onValueChange = { author = it }, label = { Text("Autor") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Nombre de la canción") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = album, onValueChange = { album = it }, label = { Text("Nombre del álbum") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = year, onValueChange = { year = it }, label = { Text("Año del álbum") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        Button(onClick = {
            message = null
            CoroutineScope(Dispatchers.IO).launch {
                val res = repo.addSong(Song(author, title, album, year))
                CoroutineScope(Dispatchers.Main).launch {
                    if (res.isSuccess) {
                        message = "Guardado correctamente"
                        // limpiar campos
                        author = ""; title = ""; album = ""; year = ""
                    } else {
                        message = res.exceptionOrNull()?.localizedMessage
                    }
                }
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Volver") }
        message?.let {
            Spacer(Modifier.height(12.dp))
            Text(it, color = MaterialTheme.colorScheme.primary)
        }
    }
}
