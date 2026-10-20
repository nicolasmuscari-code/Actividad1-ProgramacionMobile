
package com.example.proyectoapisnico.ui

import com.example.proyectoapisnico.Categoria
import com.example.proyectoapisnico.Prioridad
import com.example.proyectoapisnico.Recordatorio

/**
 * Representa los estados posibles de la pantalla de recordatorios.
 */
sealed interface ListaRecordatoriosUiState {

    // La aplicación está cargando los recordatorios.
    data object Cargando : ListaRecordatoriosUiState

    // La lista no contiene recordatorios.
    data object Vacio : ListaRecordatoriosUiState

    // La lista se cargó y contiene recordatorios.
    data class ConDatos(
        val recordatorios: List<Recordatorio>
    ) : ListaRecordatoriosUiState

    // Ocurrió un error al cargar los recordatorios.
    data class Error(
        val mensaje: String
    ) : ListaRecordatoriosUiState
}

/**
 * Conserva los datos y las validaciones del formulario.
 */
data class FormularioRecordatorioUiState(
    val titulo: String = "",
    val descripcion: String = "",
    val categoria: Categoria? = null,
    val prioridad: Prioridad? = null,
    val errorTitulo: String? = null,
    val errorDescripcion: String? = null,
    val errorGeneral: String? = null,
    val enviando: Boolean = false,
    val guardadoExitoso: Boolean = false
)
