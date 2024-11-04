package com.examples.proyecto.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun SimpleDialog(
    title: String,
    description: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = title) },
        text = { Text(text = description) },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun SuccessAdd(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Éxito") },
        text = { Text(text = "Producto agregado exitosamente.") },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Aceptar")
            }
        }
    )
}

@Composable
fun SuccesEdit(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "Éxito") },
        text = { Text(text = "Producto editado exitosamente.") },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Aceptar")
            }
        }
    )
}

