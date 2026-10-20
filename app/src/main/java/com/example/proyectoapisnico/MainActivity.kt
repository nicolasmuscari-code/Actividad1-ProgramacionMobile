
package com.example.proyectoapisnico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyectoapisnico.ui.theme.ProyectoApisNicoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ProyectoApisNicoTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    PantallaRecordatorios(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Pantalla principal con los recordatorios simulados.
@Composable
fun PantallaRecordatorios(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
            .padding(16.dp)
    ) {
        Text(
            text = "Mis recordatorios",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF24345B)
        )

        Text(
            text = "Organizá tus tareas y prioridades",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaRecordatorios) { recordatorio ->
                TarjetaRecordatorio(recordatorio)
            }
        }
    }
}

// Tarjeta que muestra los datos de cada recordatorio.
@Composable
fun TarjetaRecordatorio(recordatorio: Recordatorio) {
    val estilo = estilosCategorias[recordatorio.categoria]
    val colorCategoria = estilo?.colorHex?.let {
        Color(android.graphics.Color.parseColor(it))
    } ?: Color.Gray

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = recordatorio.titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = recordatorio.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Etiqueta(
                    texto = estilo?.etiqueta ?: recordatorio.categoria.name,
                    color = colorCategoria
                )

                Etiqueta(
                    texto = "Prioridad: ${recordatorio.prioridad.name}",
                    color = when (recordatorio.prioridad) {
                        Prioridad.BAJA -> Color(0xFF388E3C)
                        Prioridad.MEDIA -> Color(0xFFF9A825)
                        Prioridad.ALTA -> Color(0xFFD32F2F)
                    }
                )
            }

            Text(
                text = "Estado: ${recordatorio.estado.name.replace('_', ' ')}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray,
                modifier = Modifier.padding(top = 12.dp)
            )

            recordatorio.ubicacion?.let {
                Text(
                    text = "Ubicación: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            recordatorio.fechaAviso?.let {
                Text(
                    text = "Aviso: $it",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

// Etiqueta visual para categoría y prioridad.
@Composable
fun Etiqueta(texto: String, color: Color) {
    Surface(
        color = color.copy(alpha = 0.12f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = texto,
            color = color,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 6.dp
            )
        )
    }
}
