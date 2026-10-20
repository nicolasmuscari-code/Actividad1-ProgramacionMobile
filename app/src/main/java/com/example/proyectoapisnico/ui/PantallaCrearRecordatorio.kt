
package com.example.proyectoapisnico.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyectoapisnico.Categoria
import com.example.proyectoapisnico.Prioridad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCrearRecordatorio(
    estado: FormularioRecordatorioUiState,
    alCambiarTitulo: (String) -> Unit,
    alCambiarDescripcion: (String) -> Unit,
    alCambiarCategoria: (Categoria) -> Unit,
    alCambiarPrioridad: (Prioridad) -> Unit,
    alGuardar: () -> Unit,
    alCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {
    var menuCategoriaAbierto by remember { mutableStateOf(false) }
    var menuPrioridadAbierto by remember { mutableStateOf(false) }

    val tituloValido = estado.titulo.trim().length >= 4
    val descripcionValida = estado.descripcion.length <= 200
    val formularioValido = tituloValido &&
            descripcionValida &&
            estado.categoria != null &&
            estado.prioridad != null

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Nuevo recordatorio",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = estado.titulo,
            onValueChange = alCambiarTitulo,
            label = { Text("Título") },
            supportingText = {
                Text(
                    estado.errorTitulo
                        ?: "Ingresá al menos 4 caracteres."
                )
            },
            isError = estado.errorTitulo != null ||
                    (estado.titulo.isNotEmpty() && !tituloValido),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = estado.descripcion,
            onValueChange = alCambiarDescripcion,
            label = { Text("Descripción") },
            supportingText = {
                Text(
                    estado.errorDescripcion
                        ?: "${estado.descripcion.length}/200 caracteres"
                )
            },
            isError = estado.errorDescripcion != null ||
                    !descripcionValida,
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )

        // Selector de categoría.
        Column {
            Text("Categoría")

            TextButton(
                onClick = { menuCategoriaAbierto = true }
            ) {
                Text(
                    estado.categoria?.name
                        ?: "Seleccionar categoría"
                )
            }

            DropdownMenu(
                expanded = menuCategoriaAbierto,
                onDismissRequest = {
                    menuCategoriaAbierto = false
                }
            ) {
                Categoria.values().forEach { categoria ->
                    DropdownMenuItem(
                        text = { Text(categoria.name) },
                        onClick = {
                            alCambiarCategoria(categoria)
                            menuCategoriaAbierto = false
                        }
                    )
                }
            }
        }

        // Selector de prioridad.
        Column {
            Text("Prioridad")

            TextButton(
                onClick = { menuPrioridadAbierto = true }
            ) {
                Text(
                    estado.prioridad?.name
                        ?: "Seleccionar prioridad"
                )
            }

            DropdownMenu(
                expanded = menuPrioridadAbierto,
                onDismissRequest = {
                    menuPrioridadAbierto = false
                }
            ) {
                Prioridad.values().forEach { prioridad ->
                    DropdownMenuItem(
                        text = { Text(prioridad.name) },
                        onClick = {
                            alCambiarPrioridad(prioridad)
                            menuPrioridadAbierto = false
                        }
                    )
                }
            }
        }

        if (estado.errorGeneral != null) {
            Text(
                text = estado.errorGeneral,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (estado.guardadoExitoso) {
            Text(
                text = "¡Recordatorio guardado!",
                color = MaterialTheme.colorScheme.primary
            )
        }

        Button(
            onClick = alGuardar,
            enabled = formularioValido && !estado.enviando,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (estado.enviando) {
                CircularProgressIndicator()
            } else {
                Text("Guardar recordatorio")
            }
        }

        TextButton(
            onClick = alCancelar,
            enabled = !estado.enviando,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}
