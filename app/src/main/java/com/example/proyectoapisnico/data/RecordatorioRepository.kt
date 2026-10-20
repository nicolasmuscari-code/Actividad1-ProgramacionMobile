package com.example.proyectoapisnico.data

import com.example.proyectoapisnico.Recordatorio

/**
 * Define las operaciones disponibles para trabajar
 * con los recordatorios de la aplicación.
 */
interface RecordatorioRepository {

    // Obtiene todos los recordatorios.
    suspend fun obtenerRecordatorios(): List<Recordatorio>

    // Guarda un recordatorio.
    suspend fun guardarRecordatorio(
        recordatorio: Recordatorio
    ): Recordatorio

    // Busca un recordatorio por su identificador.
    suspend fun obtenerPorId(
        id: Int
    ): Recordatorio?
}