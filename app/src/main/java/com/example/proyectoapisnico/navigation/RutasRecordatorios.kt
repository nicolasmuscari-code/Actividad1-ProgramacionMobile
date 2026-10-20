
package com.example.proyectoapisnico.navigation

// Rutas de las pantallas de la aplicación.
object RutasRecordatorios {
    const val LISTADO = "listado"
    const val CREAR = "crear"
    const val DETALLE = "detalle/{recordatorioId}"

    // Genera la ruta del detalle para un recordatorio específico.
    fun detalle(recordatorioId: Int): String {
        return "detalle/$recordatorioId"
    }
}
