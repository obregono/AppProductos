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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.unison.appproductos.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartaPresentacion(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carta de Presentación", color = MaterialTheme.colorScheme.primary, fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Flecha de retroceso", tint = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.secondary)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.onBackground), // Fondo general
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Imagen circular de perfil
            Image(
                painter = painterResource(id = R.drawable.obson),
                contentDescription = "Imagen de Perfil",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .border(4.dp, MaterialTheme.colorScheme.primary, CircleShape) // Borde con color primario
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre y título centrados
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Nombre
                Text(
                    text = "Jesús Armando Obregón Zúñiga",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary // Color primario para el nombre
                )

                // Título
                Text(
                    text = "Desarrollador Back-End",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSecondary // Color onSecondary para el título
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Cuadro con información centrada
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp)) // Borde primario
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.secondary) // Fondo secundario para el cuadro
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Número de teléfono
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.tel),
                            contentDescription = "Icono Teléfono",
                            modifier = Modifier.size(24.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "6629376408",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSecondary // Color onSecondary para el texto
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Usuario o red social
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ig),
                            contentDescription = "Icono IG",
                            modifier = Modifier.size(24.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "@obregonjesus_",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSecondary // Color onSecondary para el texto
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Correo electrónico
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.mail),
                            contentDescription = "Icono Correo",
                            modifier = Modifier.size(24.dp),
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "a221208751@unison.mx",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSecondary // Color onSecondary para el texto
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

