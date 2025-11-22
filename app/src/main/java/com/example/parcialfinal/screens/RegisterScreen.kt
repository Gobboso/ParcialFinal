package com.example.parcialfinal.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Crear cuenta") }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(10.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirmar contraseña") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    message = null

                    if (password != confirmPassword) {
                        message = "Las contraseñas no coinciden"
                        return@Button
                    }

                    auth.createUserWithEmailAndPassword(email.trim(), password)
                        .addOnSuccessListener {
                            message = "Cuenta creada correctamente"
                            onRegisterSuccess()
                        }
                        .addOnFailureListener {
                            message = it.localizedMessage
                        }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarse")
            }

            Spacer(Modifier.height(10.dp))

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver")
            }

            Spacer(Modifier.height(20.dp))

            message?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
