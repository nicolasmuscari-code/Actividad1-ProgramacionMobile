package com.example.proyectoapisnico.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyectoapisnico.data.RecordatorioRepository

/**
 * Crea el ViewModel y le proporciona el repositorio necesario.
 */
class RecordatorioViewModelFactory(
    private val repositorio: RecordatorioRepository
) : ViewModelProvider.Factory {

    // Construye el ViewModel con el repositorio recibido.
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordatorioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecordatorioViewModel(repositorio) as T
        }

        throw IllegalArgumentException(
            "ViewModel desconocido: ${modelClass.name}"
        )
    }
}