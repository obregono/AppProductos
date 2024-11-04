package com.unison.appproductos.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.unison.appproductos.models.Producto
import com.examples.proyecto.dialogs.SimpleDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListadoProductos(
    productos: List<Producto>,
    onAgregarProductoClick: () -> Unit,
    onDeleteClick: (Producto) -> Unit,
    navController: NavHostController
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Listado de Productos", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("inicio") }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar a Inicio", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAgregarProductoClick,
                containerColor = MaterialTheme.colorScheme.secondary, // Usar color del tema
                contentColor = MaterialTheme.colorScheme.onSecondary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar Producto")
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Color de fondo del tema
        ) {
            if (productos.isEmpty()) {
                Text(
                    text = "No hay productos disponibles",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground // Color del texto
                    )
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(productos) { producto ->
                        ProductoItem(
                            producto = producto,
                            onDeleteClick = {
                                productoAEliminar = producto
                                showDeleteDialog = true
                            },
                            onEditClick = {
                                navController.navigate("editarProducto/${producto.id}")
                            }
                        )
                    }
                }
            }

            if (showDeleteDialog) {
                SimpleDialog(
                    title = "Confirmar eliminación",
                    description = "¿Estás seguro de que quieres eliminar este producto?",
                    onConfirm = {
                        productoAEliminar?.let { onDeleteClick(it) }
                        showDeleteDialog = false
                    },
                    onDismiss = { showDeleteDialog = false }
                )
            }
        }
    }
}

@Composable
fun ProductoItem(
    producto: Producto,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable(onClick = onEditClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary), // Color de la tarjeta
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
            // .border(2.dp, MaterialTheme.colorScheme.secondary) // Eliminar esta línea para quitar el borde
        ) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary // Color del texto
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$${String.format("%.2f", producto.precio)}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary // Color del texto
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Editar Producto",
                        tint = MaterialTheme.colorScheme.onPrimary // Color del icono
                    )
                }

                IconButton(onClick = onDeleteClick) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Eliminar Producto",
                        tint = MaterialTheme.colorScheme.error // Color de error
                    )
                }
            }
        }
    }
}


