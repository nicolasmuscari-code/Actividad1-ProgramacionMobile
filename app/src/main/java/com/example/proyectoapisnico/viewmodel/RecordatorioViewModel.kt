
package com.example.proyectoapisnico.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectoapisnico.Categoria
import com.example.proyectoapisnico.EstadoRecordatorio
import com.example.proyectoapisnico.Prioridad
import com.example.proyectoapisnico.Recordatorio
import com.example.proyectoapisnico.data.RecordatorioRepository
import com.example.proyectoapisnico.ui.FormularioRecordatorioUiState
import com.example.proyectoapisnico.ui.ListaRecordatoriosUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Administra los datos y las operaciones de los recordatorios.
class RecordatorioViewModel(
    private val repositorio: RecordatorioRepository
) : ViewModel() {

    // Estado observable del listado.
    private val _estadoLista =
        MutableStateFlow<ListaRecordatoriosUiState>(
            ListaRecordatoriosUiState.Cargando
        )

    val estadoLista: StateFlow<ListaRecordatoriosUiState> =
        _estadoLista.asStateFlow()

    // Estado observable del formulario.
    private val _estadoFormulario =
        MutableStateFlow(FormularioRecordatorioUiState())

    val estadoFormulario: StateFlow<FormularioRecordatorioUiState> =
        _estadoFormulario.asStateFlow()

    // Estado observable del recordatorio seleccionado.
    private val _recordatorioSeleccionado =
        MutableStateFlow<Recordatorio?>(null)

    val recordatorioSeleccionado: StateFlow<Recordatorio?> =
        _recordatorioSeleccionado.asStateFlow()

    // Carga los recordatorios desde el repositorio.
    fun cargarRecordatorios() {
        viewModelScope.launch {
            _estadoLista.value = ListaRecordatoriosUiState.Cargando

            try {
                actualizarEstadoLista(
                    repositorio.obtenerRecordatorios()
                )
            } catch (e: Exception) {
                _estadoLista.value = ListaRecordatoriosUiState.Error(
                    e.message ?: "No se pudieron cargar los recordatorios."
                )
            }
        }
    }

    // Actualiza el estado de la lista con los datos recibidos.
    private fun actualizarEstadoLista(
        recordatorios: List<Recordatorio>
    ) {
        _estadoLista.value =
            if (recordatorios.isEmpty()) {
                ListaRecordatoriosUiState.Vacio
            } else {
                ListaRecordatoriosUiState.ConDatos(recordatorios)
            }
    }

    // Busca un recordatorio por su ID.
    fun cargarDetalle(recordatorioId: Int) {
        viewModelScope.launch {
            try {
                _recordatorioSeleccionado.value =
                    repositorio.obtenerPorId(recordatorioId)
            } catch (e: Exception) {
                _recordatorioSeleccionado.value = null
            }
        }
    }

    // Actualiza el título del formulario.
    fun actualizarTitulo(titulo: String) {
        _estadoFormulario.value = _estadoFormulario.value.copy(
            titulo = titulo,
            errorTitulo = null,
            errorGeneral = null,
            guardadoExitoso = false
        )
    }

    // Actualiza la descripción del formulario.
    fun actualizarDescripcion(descripcion: String) {
        _estadoFormulario.value = _estadoFormulario.value.copy(
            descripcion = descripcion,
            errorDescripcion = null,
            errorGeneral = null,
            guardadoExitoso = false
        )
    }

    // Actualiza la categoría seleccionada.
    fun actualizarCategoria(categoria: Categoria) {
        _estadoFormulario.value = _estadoFormulario.value.copy(
            categoria = categoria,
            errorGeneral = null,
            guardadoExitoso = false
        )
    }

    // Actualiza la prioridad seleccionada.
    fun actualizarPrioridad(prioridad: Prioridad) {
        _estadoFormulario.value = _estadoFormulario.value.copy(
            prioridad = prioridad,
            errorGeneral = null,
            guardadoExitoso = false
        )
    }

    // Valida y guarda el nuevo recordatorio.
    fun guardarRecordatorio() {
        val formulario = _estadoFormulario.value
        val tituloLimpio = formulario.titulo.trim()
        val descripcion = formulario.descripcion

        // Valida que el título tenga al menos cuatro caracteres.
        val errorTitulo =
            if (tituloLimpio.length < 4) {
                "El título debe tener al menos 4 caracteres."
            } else {
                null
            }

        // Valida que la descripción no supere los 200 caracteres.
        val errorDescripcion =
            if (descripcion.length > 200) {
                "La descripción no puede superar los 200 caracteres."
            } else {
                null
            }

        val faltaCategoria = formulario.categoria == null
        val faltaPrioridad = formulario.prioridad == null

        // Si hay errores, los informa y no guarda.
        if (
            errorTitulo != null ||
            errorDescripcion != null ||
            faltaCategoria ||
            faltaPrioridad
        ) {
            _estadoFormulario.value = formulario.copy(
                errorTitulo = errorTitulo,
                errorDescripcion = errorDescripcion,
                errorGeneral =
                    if (faltaCategoria || faltaPrioridad) {
                        "Seleccioná una categoría y una prioridad."
                    } else {
                        null
                    }
            )
            return
        }

        viewModelScope.launch {
            _estadoFormulario.value = formulario.copy(
                enviando = true,
                errorGeneral = null
            )

            try {
                // Obtiene el siguiente ID disponible.
                val recordatoriosExistentes =
                    repositorio.obtenerRecordatorios()

                val nuevoId =
                    (recordatoriosExistentes.maxOfOrNull { it.id } ?: 0) + 1

                // Construye el nuevo recordatorio.
                val nuevoRecordatorio = Recordatorio(
                    id = nuevoId,
                    titulo = tituloLimpio,
                    descripcion = descripcion.trim(),
                    categoria = formulario.categoria!!,
                    prioridad = formulario.prioridad!!,
                    estado = EstadoRecordatorio.PENDIENTE
                )

                // Guarda el nuevo recordatorio.
                repositorio.guardarRecordatorio(nuevoRecordatorio)

                // Vuelve a consultar los datos y actualiza el listado.
                val recordatoriosActualizados =
                    repositorio.obtenerRecordatorios()

                actualizarEstadoLista(recordatoriosActualizados)

                // Informa que el guardado terminó correctamente.
                _estadoFormulario.value = FormularioRecordatorioUiState(
                    guardadoExitoso = true
                )
            } catch (e: Exception) {
                _estadoFormulario.value = _estadoFormulario.value.copy(
                    errorGeneral = e.message
                        ?: "No se pudo guardar el recordatorio."
                )
            } finally {
                _estadoFormulario.value =
                    _estadoFormulario.value.copy(enviando = false)
            }
        }
    }

    // Limpia el formulario al iniciar una nueva creación.
    fun limpiarFormulario() {
        _estadoFormulario.value = FormularioRecordatorioUiState()
    }
}
