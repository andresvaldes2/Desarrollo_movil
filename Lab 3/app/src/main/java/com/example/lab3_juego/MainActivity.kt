package com.example.lab3_juego

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab3_juego.ui.theme.Lab3JuegoTheme
import kotlin.random.Random
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab3JuegoTheme {
                JuegoAdivinarNumero()
            }
        }
    }
}

@Composable
fun JuegoAdivinarNumero() {
    var numeroSecreto by remember { mutableStateOf(Random.nextInt(0, 101)) }
    var intentos by remember { mutableStateOf(3) }
    var mensaje by remember { mutableStateOf("") }
    var input by remember { mutableStateOf("") }
    var juegoFinalizado by remember { mutableStateOf(false) }

    var tiempoRestante by remember { mutableStateOf(60) } // en segundos
    var tiempoInicio by remember { mutableStateOf(System.currentTimeMillis()) }

    // Contador de tiempo en segundos
    LaunchedEffect(tiempoInicio) {
        while (tiempoRestante > 0 && !juegoFinalizado) {
            delay(1000)
            tiempoRestante--
        }
        if (!juegoFinalizado) {
            mensaje = "¡Tiempo agotado! El número era $numeroSecreto."
            juegoFinalizado = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Juego Adivinar Número",
                fontSize = 28.sp,
                color = Color.Red
            )
            Text(
                text = "Andres Valdés 6-726-1938",
                fontSize = 20.sp,
                color = Color.Yellow
            )

            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_help),
                contentDescription = "Imagen de ayuda",
                modifier = Modifier.size(200.dp)
            )

            Text(
                text = "Adivina el número entre 0 y 100. Solo tienes 3 intentos o 1 minuto.",
                color = Color.Yellow
            )

            Text(
                text = "Tiempo restante: $tiempoRestante segundos",
                color = Color.Cyan,
                fontSize = 18.sp
            )

            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Ingresa tu número", color = Color.White) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray,
                    cursorColor = Color.Yellow
                )
            )

            Button(
                onClick = {
                    val numeroJugador = input.toIntOrNull()
                    if (numeroJugador != null && numeroJugador in 0..100) {
                        if (numeroJugador == numeroSecreto) {
                            mensaje = "¡Felicidades! Adivinaste el número."
                            juegoFinalizado = true
                        } else {
                            intentos--
                            if (intentos == 0) {
                                mensaje = "¡Perdiste! El número era $numeroSecreto."
                                juegoFinalizado = true
                            } else {
                                mensaje = if (numeroJugador < numeroSecreto)
                                    "El número secreto es mayor."
                                else
                                    "El número secreto es menor."
                            }
                        }
                    } else {
                        mensaje = "Ingresa un número válido entre 0 y 100."
                    }
                    input = ""
                },
                enabled = !juegoFinalizado,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Adivinar", color = Color.White)
            }

            Text(text = mensaje, color = Color.Yellow)
            Text(text = "Intentos restantes: $intentos", color = Color(0xFFFFA500)) // Naranja

            if (juegoFinalizado) {
                Button(
                    onClick = {
                        numeroSecreto = Random.nextInt(0, 101)
                        intentos = 3
                        mensaje = ""
                        juegoFinalizado = false
                        input = ""
                        tiempoInicio = System.currentTimeMillis()
                        tiempoRestante = 60
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)) // Naranja
                ) {
                    Text("Reiniciar Juego", color = Color.Black)
                }
            }
        }
    }
}
