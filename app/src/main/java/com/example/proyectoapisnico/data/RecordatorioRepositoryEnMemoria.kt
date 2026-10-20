package com.example.proyectoapisnico.data

import com.example.proyectoapisnico.Recordatorio
import com.example.proyectoapisnico.listaRecordatorios

/**
 * Implementa el repositorio utilizando una lista en memoria.
 * Los datos se conservan mientras la aplicación está ejecutándose,
 * pero no quedan guardados al cerrar la aplicación.
 */
class RecordatorioRepositoryEnMemoria : RecordatorioRepository {

    // Copia mutable de los recordatorios iniciales de la API 1.
    private val recordatorios = listaRecordatorios.toMutableList()

    // Devuelve todos los recordatorios disponibles.
    override suspend fun obtenerRecordatorios(): List<Recordatorio> {
        return recordatorios.toList()
    }

    // Guarda un recordatorio nuevo o actualiza uno existente.
    override suspend fun guardarRecordatorio(
        recordatorio: Recordatorio
    ): Recordatorio {

        val indice = recordatorios.indexOfFirst {
            it.id == recordatorio.id
        }

        if (indice >= 0) {
            // Si el ID existe, reemplaza el recordatorio.
            recordatorios[indice] = recordatorio
        } else {
            // Si el ID no existe, agrega el recordatorio.
            recordatorios.add(recordatorio)
        }

        return recordatorio
    }

    // Busca un recordatorio por su ID.
    // Devuelve null si no lo encuentra.
    override suspend fun obtenerPorId(
        id: Int
    ): Recordatorio? {
        return recordatorios.find { it.id == id }
    }
}