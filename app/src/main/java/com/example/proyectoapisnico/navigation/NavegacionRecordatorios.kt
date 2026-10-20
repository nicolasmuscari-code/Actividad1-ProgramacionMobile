
package com.example.proyectoapisnico.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectoapisnico.data.RecordatorioRepositoryEnMemoria
import com.example.proyectoapisnico.ui.PantallaCrearRecordatorio
import com.example.proyectoapisnico.ui.PantallaDetalleRecordatorio
import com.example.proyectoapisnico.ui.PantallaListadoRecordatorios
import com.example.proyectoapisnico.viewmodel.RecordatorioViewModel
import com.example.proyectoapisnico.viewmodel.RecordatorioViewModelFactory

@Composable
fun NavegacionRecordatorios() {

    val navController = rememberNavController()

    // Mantiene una única instancia del repositorio durante la navegación.
    val repositorio = remember {
        RecordatorioRepositoryEnMemoria()
    }

    // ViewModel compartido por las tres pantallas.
    val viewModel: RecordatorioViewModel = viewModel(
        factory = RecordatorioViewModelFactory(repositorio)
    )

    // Estado observable del listado.
    val estadoLista by
    viewModel.estadoLista.collectAsStateWithLifecycle()

    // Estado observable del formulario.
    val estadoFormulario by
    viewModel.estadoFormulario.collectAsStateWithLifecycle()

    // Recordatorio seleccionado para ver el detalle.
    val recordatorioSeleccionado by
    viewModel.recordatorioSeleccionado.collectAsStateWithLifecycle()

    // Carga inicial del listado.
    LaunchedEffect(viewModel) {
        viewModel.cargarRecordatorios()
    }

    // Vuelve al listado cuando el guardado termina correctamente.
    LaunchedEffect(estadoFormulario.guardadoExitoso) {
        if (estadoFormulario.guardadoExitoso) {
            navController.popBackStack()
            viewModel.limpiarFormulario()
        }
    }

    NavHost(
        navController = navController,
        startDestination = RutasRecordatorios.LISTADO
    ) {

        // Pantalla principal: listado de recordatorios.
        composable(RutasRecordatorios.LISTADO) {
            PantallaListadoRecordatorios(
                estado = estadoLista,
                alCrear = {
                    viewModel.limpiarFormulario()
                    navController.navigate(RutasRecordatorios.CREAR)
                },
                alSeleccionar = { id ->
                    navController.navigate(
                        RutasRecordatorios.detalle(id)
                    )
                },
                alReintentar = {
                    viewModel.cargarRecordatorios()
                }
            )
        }

        // Pantalla para crear un recordatorio.
        composable(RutasRecordatorios.CREAR) {
            PantallaCrearRecordatorio(
                estado = estadoFormulario,
                alCambiarTitulo = viewModel::actualizarTitulo,
                alCambiarDescripcion = viewModel::actualizarDescripcion,
                alCambiarCategoria = viewModel::actualizarCategoria,
                alCambiarPrioridad = viewModel::actualizarPrioridad,
                alGuardar = viewModel::guardarRecordatorio,
                alCancelar = {
                    viewModel.limpiarFormulario()
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de detalle: recibe el ID por la ruta.
        composable(
            route = RutasRecordatorios.DETALLE,
            arguments = listOf(
                navArgument("recordatorioId") {
                    type = NavType.IntType
                }
            )
        ) { entrada ->

            val id = entrada.arguments?.getInt("recordatorioId")

            LaunchedEffect(id) {
                if (id != null) {
                    viewModel.cargarDetalle(id)
                }
            }

            PantallaDetalleRecordatorio(
                recordatorio = recordatorioSeleccionado,
                alVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}
