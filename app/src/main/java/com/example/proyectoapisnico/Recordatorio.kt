
package com.example.proyectoapisnico

// Categorías disponibles para clasificar los recordatorios.
enum class Categoria {
    TRABAJO,
    ESTUDIO,
    PERSONAL,
    SALUD
}

// Prioridades que puede tener un recordatorio.
enum class Prioridad {
    BAJA,
    MEDIA,
    ALTA
}

// Estados posibles de un recordatorio.
enum class EstadoRecordatorio {
    PENDIENTE,
    EN_PROGRESO,
    COMPLETADO
}


/*
 * Representa un recordatorio de la aplicación.
 * Los atributos son inmutables porque usamos val.
 */
data class Recordatorio(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val categoria: Categoria,
    val prioridad: Prioridad,
    val estado: EstadoRecordatorio,

    // Datos opcionales: pueden no estar definidos.
    val ubicacion: String? = null,
    val fechaAviso: String? = null
)


/*
 * Representa las opciones de aviso de un recordatorio.
 * La clase sellada limita las variantes posibles.
 */
sealed class AvisoRecordatorio {

    // Aviso asociado a una fecha y hora.
    data class PorFecha(
        val fechaHora: String
    ) : AvisoRecordatorio()

    // Aviso asociado a una ubicación.
    data class PorUbicacion(
        val ubicacion: String
    ) : AvisoRecordatorio()

    // El recordatorio no tiene un aviso configurado.
    data object SinAviso : AvisoRecordatorio()
}

