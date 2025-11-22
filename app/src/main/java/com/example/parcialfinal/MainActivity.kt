package com.example.parcialfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.parcialfinal.ui.NavGraph
import com.example.parcialfinal.ui.theme.ParcialFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 1. Usamos el tema de tu proyecto, que ya está basado en Material 3.
            ParcialFinalTheme {
                // 2. Usamos el componente Surface de Material 3.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    // 3. Accedemos a los colores con "colorScheme", que es la forma correcta en M3.
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 4. Tu NavGraph se renderiza dentro del Surface.
                    NavGraph()
                }
            }
        }
    }
}
