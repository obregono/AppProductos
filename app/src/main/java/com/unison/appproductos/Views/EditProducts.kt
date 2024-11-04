package com.unison.appproductos.views

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.unison.appproductos.models.Producto
import com.unison.appproductos.viewmodels.ProductosViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarProducto(
    navController: NavHostController,
    viewModel: ProductosViewModel,
    productoId: String
) {
    var producto by remember { mutableStateOf<Producto?>(null) }
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var fechaRegistro by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }
    var showSuccessEditDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Configuración del DatePicker
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                fechaRegistro = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    LaunchedEffect(productoId) {
        producto = viewModel.productDatabaseDao.getProductById(productoId)
        producto?.let {
            nombre = it.nombre
            descripcion = it.descripcion
            precio = it.precio.toString()
            fechaRegistro = it.fechaRegistro
        }
        isLoading = false
    }

    // Función de validación de campos
    fun validarCampos(): Boolean {
        return when {
            nombre.isEmpty() -> {
                errorMessage = "El nombre es obligatorio."
                false
            }
            descripcion.isEmpty() -> {
                errorMessage = "La descripción es obligatoria."
                false
            }
            precio.isEmpty() || precio.toDoubleOrNull() == null -> {
                errorMessage = "El precio es obligatorio y debe ser un número válido."
                false
            }
            fechaRegistro.isEmpty() -> {
                errorMessage = "La fecha de registro es obligatoria."
                false
            }
            else -> {
                errorMessage = ""
                true
            }
        }
    }

    if (isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize() // Cambiado a fillMaxSize para llenar toda la pantalla
                .background(Color(0xFF272727)) // Fondo color 0xFF272727
                .padding(16.dp) // Mantener el padding interno
        ) {
            // Icono de regresar
            IconButton(onClick = { navController.navigate("listado") }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Regresar", tint = Color.White)
            }
            Text("Editar Producto", style = MaterialTheme.typography.headlineLarge.copy(color = Color.White))
            Spacer(modifier = Modifier.height(16.dp))

            // Campo para el nombre
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre", color = MaterialTheme.colorScheme.onPrimary) },
                isError = nombre.isEmpty(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            if (nombre.isEmpty()) {
                Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Campo para la descripción
            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción", color = MaterialTheme.colorScheme.onPrimary) },
                isError = descripcion.isEmpty(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            if (descripcion.isEmpty()) {
                Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Campo para el precio
            TextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio", color = MaterialTheme.colorScheme.onPrimary) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = precio.isEmpty() || precio.toDoubleOrNull() == null,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            when {
                precio.isEmpty() -> Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
                precio.toDoubleOrNull() == null -> Text(text = "Debe ser un número válido", color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Campo para la fecha de registro
            TextField(
                value = fechaRegistro,
                onValueChange = { fechaRegistro = it },
                label = { Text("Fecha de Registro", color = MaterialTheme.colorScheme.onPrimary) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                readOnly = true,
                isError = fechaRegistro.isEmpty(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    containerColor = MaterialTheme.colorScheme.onBackground,
                    focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onPrimary
                )
            )
            if (fechaRegistro.isEmpty()) {
                Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botón de cancelar
                Button(
                    onClick = {
                        navController.navigate("listado") // Regresar a la lista
                    },
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Cancelar", color = MaterialTheme.colorScheme.onPrimary)
                }

                // Botón de guardar cambios
                Button(
                    onClick = {
                        if (validarCampos()) {
                            viewModel.actualizarProducto(
                                productoId,
                                nombre,
                                descripcion,
                                precio.toDouble(),
                                fechaRegistro
                            )
                            showSuccessEditDialog = true
                        }
                    },
                    modifier = Modifier.weight(1f).padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Guardar Cambios", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }

    // Mostrar diálogo de éxito
    if (showSuccessEditDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessEditDialog = false },
            title = { Text("Éxito", color = MaterialTheme.colorScheme.onPrimary) },
            text = { Text("Producto editado exitosamente.", color = MaterialTheme.colorScheme.onPrimary) },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessEditDialog = false
                    navController.navigate("listado") // Regresar a la lista
                }) {
                    Text("Aceptar", color = MaterialTheme.colorScheme.onPrimary)
                }
            },
            containerColor = MaterialTheme.colorScheme.secondary
        )
    }
}
