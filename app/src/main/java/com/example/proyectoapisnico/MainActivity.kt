
package com.example.proyectoapisnico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.proyectoapisnico.navigation.NavegacionRecordatorios
import com.example.proyectoapisnico.ui.theme.ProyectoApisNicoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permite que la aplicación aproveche toda la pantalla.
        enableEdgeToEdge()

        setContent {
            // Aplica el tema visual de la aplicación.
            ProyectoApisNicoTheme {

                // Estructura principal de la aplicación.
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    // Respeta los márgenes internos del Scaffold.
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {

                        // Administra la navegación entre las pantallas.
                        NavegacionRecordatorios()
                    }
                }
            }
        }
    }
}
