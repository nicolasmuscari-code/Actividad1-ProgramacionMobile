
package com.example.proyectoapisnico.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyectoapisnico.EstiloCategoria
import com.example.proyectoapisnico.Recordatorio

@Composable
fun PantallaDetalleRecordatorio(
    recordatorio: Recordatorio?,
    alVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Detalle del recordatorio",
            style = MaterialTheme.typography.headlineMedium
        )

        if (recordatorio == null) {
            Text(
                text = "No se encontró el recordatorio.",
                color = MaterialTheme.colorScheme.error
            )
        } else {
            Text(
                text = recordatorio.titulo,
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Descripción: ${recordatorio.descripcion}"
            )

            Text(
                text = "Categoría: ${recordatorio.categoria.name}"
            )

            Text(
                text = "Prioridad: ${recordatorio.prioridad.name}"
            )

            Text(
                text = "Estado: ${recordatorio.estado.name.replace('_', ' ')}"
            )

            recordatorio.ubicacion?.let { ubicacion ->
                Text(text = "Ubicación: $ubicacion")
            }

            recordatorio.fechaAviso?.let { fecha ->
                Text(text = "Aviso: $fecha")
            }
        }

        Button(
            onClick = alVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al listado")
        }
    }
}
