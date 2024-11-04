package com.unison.appproductos.views

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.examples.proyecto.dialogs.SimpleDialog
import com.unison.appproductos.viewmodels.ProductosViewModel
import java.util.*
import com.examples.proyecto.dialogs.SuccessAdd

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioProductos(
    viewModel: ProductosViewModel,
    onAgregarClick: () -> Unit,
    navController: NavHostController
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var fechaRegistro by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

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

    // Validación de campos
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Formulario de Productos", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("listado") }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Flecha hacia atrás", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary) // Color de fondo del TopAppBar
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (validarCampos()) {
                        viewModel.agregarProducto(nombre, descripcion, precio.toDouble(), fechaRegistro)
                        onAgregarClick()
                        navController.navigate("listado")
                        showSuccessDialog = true
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondary, // Color del botón flotante
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar Producto")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background) // Fondo de la columna
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Campo para el nombre
            Text(text = "Nombre:", color = MaterialTheme.colorScheme.onBackground)
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                ),
                isError = nombre.isEmpty()
            )
            if (nombre.isEmpty()) {
                Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para la descripción
            Text(text = "Descripción:", color = MaterialTheme.colorScheme.onBackground)
            TextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                ),
                isError = descripcion.isEmpty()
            )
            if (descripcion.isEmpty()) {
                Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para el precio
            Text(text = "Precio:", color = MaterialTheme.colorScheme.onBackground)
            TextField(
                value = precio,
                onValueChange = { precio = it },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                ),
                isError = precio.isEmpty() || precio.toDoubleOrNull() == null
            )
            if (precio.isEmpty()) {
                Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
            } else if (precio.toDoubleOrNull() == null) {
                Text(text = "Debe ser un número válido", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para la fecha de registro
            Text(text = "Fecha de Registro:", color = MaterialTheme.colorScheme.onBackground)
            TextField(
                value = fechaRegistro,
                onValueChange = { fechaRegistro = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() }, // Abre el DatePicker
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.background,
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSecondary,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                ),
                isError = fechaRegistro.isEmpty()
            )
            if (fechaRegistro.isEmpty()) {
                Text(text = "Este campo es obligatorio", color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }

            if (showSuccessDialog) {
                SuccessAdd(onDismiss = { showSuccessDialog = false })
            }
        }
    }
}

