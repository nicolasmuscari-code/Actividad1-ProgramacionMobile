
package com.example.proyectoapisnico

// Lista con los recordatorios simulados de la aplicación.
val listaRecordatorios = listOf(
    Recordatorio(
        id = 1,
        titulo = "Entregar trabajo práctico",
        descripcion = "Finalizar la actividad de programación",
        categoria = Categoria.ESTUDIO,
        prioridad = Prioridad.ALTA,
        estado = EstadoRecordatorio.PENDIENTE
    ),
    Recordatorio(
        id = 2,
        titulo = "Revisar pendientes del trabajo",
        descripcion = "Organizar las tareas de la semana",
        categoria = Categoria.TRABAJO,
        prioridad = Prioridad.MEDIA,
        estado = EstadoRecordatorio.EN_PROGRESO
    ),
    Recordatorio(
        id = 3,
        titulo = "Sacar turno médico",
        descripcion = "Solicitar un turno de control",
        categoria = Categoria.SALUD,
        prioridad = Prioridad.ALTA,
        estado = EstadoRecordatorio.PENDIENTE,
        ubicacion = "Centro médico"
    ),
    Recordatorio(
        id = 4,
        titulo = "Comprar materiales",
        descripcion = "Comprar materiales para un proyecto personal",
        categoria = Categoria.PERSONAL,
        prioridad = Prioridad.BAJA,
        estado = EstadoRecordatorio.COMPLETADO,
        fechaAviso = "2026-10-20 10:00"
    )
)



/*
 * Set: obtiene las categorías utilizadas sin repetirlas.
 */
val categoriasDisponibles: Set<Categoria> =
    listaRecordatorios.map { it.categoria }.toSet()

/*
 * Map: asocia cada categoría con una etiqueta y un color.
 */
data class EstiloCategoria(
    val etiqueta: String,
    val colorHex: String
)

val estilosCategorias: Map<Categoria, EstiloCategoria> = mapOf(
    Categoria.TRABAJO to EstiloCategoria("Trabajo", "#1976D2"),
    Categoria.ESTUDIO to EstiloCategoria("Estudio", "#7B1FA2"),
    Categoria.PERSONAL to EstiloCategoria("Personal", "#388E3C"),
    Categoria.SALUD to EstiloCategoria("Salud", "#D32F2F")
)

/*
 * Filtra los recordatorios por estado.
 */
fun filtrarPorEstado(
    estado: EstadoRecordatorio
): List<Recordatorio> {
    return listaRecordatorios.filter { it.estado == estado }
}

/*
 * Filtra los recordatorios por prioridad.
 */
fun filtrarPorPrioridad(
    prioridad: Prioridad
): List<Recordatorio> {
    return listaRecordatorios.filter { it.prioridad == prioridad }
}
