package com.unison.appproductos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unison.appproductos.viewmodels.ProductosViewModel
import com.unison.appproductos.views.*

@Composable
fun NavManager(
    navController: NavHostController,
    viewModel: ProductosViewModel
) {
    val productosState = viewModel.productos.collectAsState()

    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            Inicio(
                onProductosClick = { navController.navigate("listado") },
                onPresentacionClick = { navController.navigate("presentacion") },
                navController = navController
            )
        }
        composable("listado") {
            ListadoProductos(
                productos = productosState.value,
                onAgregarProductoClick = { navController.navigate("formulario") },
                onDeleteClick = { viewModel.eliminarProducto(it) },
                navController = navController
            )
        }
        composable("formulario") {
            FormularioProductos(
                viewModel = viewModel,
                onAgregarClick = {
                    navController.navigate("listado")
                },
                navController = navController
            )
        }
        composable("presentacion") {
            CartaPresentacion(navController)
        }
        composable("editarProducto/{productoId}") { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("productoId")
            if (productoId != null) {
                EditarProducto(
                    navController = navController,
                    viewModel = viewModel,
                    productoId = productoId
                )
            }
        }

    }
}

