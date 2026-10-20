
package com.example.proyectoapisnico.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PantallaListadoRecordatorios(
    estado: ListaRecordatoriosUiState,
    alCrear: () -> Unit,
    alSeleccionar: (Int) -> Unit,
    alReintentar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
            .padding(16.dp)
    ) {
        Text(
            text = "Mis recordatorios",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF24345B)
        )

        Text(
            text = "Organizá tus tareas y prioridades",
            modifier = Modifier.padding(
                top = 4.dp,
                bottom = 16.dp
            ),
            style = MaterialTheme.typography.bodyMedium
        )

        when (estado) {
            is ListaRecordatoriosUiState.Cargando -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()

                    Text(
                        text = "Cargando recordatorios...",
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            is ListaRecordatoriosUiState.Vacio -> {
                Text(text = "Todavía no tenés recordatorios.")

                Button(
                    onClick = alCrear,
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Text(text = "Crear recordatorio")
                }
            }

            is ListaRecordatoriosUiState.ConDatos -> {

                Button(
                    onClick = alCrear,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Nuevo recordatorio")
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = estado.recordatorios,
                        key = { recordatorio -> recordatorio.id }
                    ) { recordatorio ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    alSeleccionar(recordatorio.id)
                                }
                        ) {
                            TarjetaRecordatorio(recordatorio)
                        }
                    }
                }
            }

            is ListaRecordatoriosUiState.Error -> {
                Text(
                    text = estado.mensaje,
                    color = MaterialTheme.colorScheme.error
                )

                Button(
                    onClick = alReintentar,
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Text(text = "Reintentar")
                }
            }
        }
    }
}
