package com.example.myapplication

// Componentes.kt — pedaços de tela reaproveitados em várias telas
// (Composable pequeno e reutilizável = menos código repetido)

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Foto do pet. Se ainda não existe foto (fotoRes == null), mostra uma patinha.
@Composable
fun FotoPet(pet: Pet, modifier: Modifier = Modifier) {
    val foto = pet.fotoRes
    if (foto != null) {
        Image(
            painter = painterResource(id = foto),
            contentDescription = pet.nome,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = modifier.background(Cores.Laranja),
            contentAlignment = Alignment.Center
        ) {
            Text("🐾", fontSize = 40.sp)
        }
    }
}

// TopAppBar com botão de VOLTAR (usada nas telas de detalhe e formulário).
// Recebe onVoltar como lambda — a tela não conhece o navController.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraTopo(titulo: String, onVoltar: () -> Unit) {
    TopAppBar(
        title = { Text(titulo) },
        navigationIcon = {
            IconButton(onClick = onVoltar) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Cores.Teal,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

// "Etiqueta" arredondada (ex.: "Filhote", "Vacinado")
@Composable
fun Etiqueta(texto: String) {
    Surface(shape = RoundedCornerShape(50), color = Cores.TealClaro) {
        Text(
            text = texto,
            color = Cores.Texto,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}
