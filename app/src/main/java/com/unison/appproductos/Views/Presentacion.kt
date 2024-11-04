package com.unison.appproductos.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.unison.appproductos.R

@Composable
fun Inicio(
    onProductosClick: () -> Unit,
    onPresentacionClick: () -> Unit,
    navController: NavHostController
) {
    // Diseño general de la columna sin borde alrededor
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground), // Color de fondo más amigable
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo con bordes más suaves
        Image(
            painter = painterResource(id = R.drawable.logo_unison),
            contentDescription = "Logo UNISON",
            modifier = Modifier
                .size(180.dp)
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.inversePrimary, // Borde más visible
                    shape = CircleShape
                )
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Texto con estilo mejorado
        Text(
            text = "Universidad de Sonora",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(8.dp), // Espaciado adicional
            style = MaterialTheme.typography.headlineLarge.copy( // Uso de un estilo más grande
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Contenedor para botones
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center // Centra el contenido
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onProductosClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    shape = RoundedCornerShape(12.dp), // Bordes redondeados
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp) // Espaciado a la derecha
                ) {
                    Text("Productos", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                }

                Button(
                    onClick = onPresentacionClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    shape = RoundedCornerShape(12.dp), // Bordes redondeados
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp) // Espaciado a la izquierda
                ) {
                    Text("Presentación", fontSize = 18.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
