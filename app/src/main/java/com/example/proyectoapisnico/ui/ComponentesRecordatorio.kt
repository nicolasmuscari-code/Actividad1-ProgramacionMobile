
package com.example.proyectoapisnico.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.proyectoapisnico.EstiloCategoria
import com.example.proyectoapisnico.Prioridad
import com.example.proyectoapisnico.Recordatorio
import com.example.proyectoapisnico.estilosCategorias

// Tarjeta reutilizable que muestra los datos del recordatorio.
@Composable
fun TarjetaRecordatorio(recordatorio: Recordatorio) {

    val estilo = estilosCategorias[recordatorio.categoria]

    val colorCategoria = estilo?.colorHex?.let { colorHex ->
        Color(android.graphics.Color.parseColor(colorHex))
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
                modifier = Modifier.padding(
                    top = 4.dp,
                    bottom = 12.dp
                )
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

            recordatorio.ubicacion?.let { ubicacion ->
                Text(
                    text = "Ubicación: $ubicacion",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            recordatorio.fechaAviso?.let { fecha ->
                Text(
                    text = "Aviso: $fecha",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

// Etiqueta visual reutilizable para categoría y prioridad.
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
