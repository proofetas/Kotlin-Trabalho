package com.example.myapplication

// AbaPerfil.kt — números do app (calculados do ViewModel) + atalhos de cadastro.

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AbaPerfil(
    viewModel: MeuViewModel,
    onCadastrarPet: () -> Unit,
    onCadastrarOng: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cores.Fundo)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text("🐶", fontSize = 64.sp)
        Text("Amigo dos pets", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Cores.Texto)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Numero("Pets", viewModel.pets.size, Modifier.weight(1f))
            Numero("ONGs", viewModel.ongs.size, Modifier.weight(1f))
            Numero("Favoritos", viewModel.pets.count { it.favorito }, Modifier.weight(1f))
            Numero("Encontros", viewModel.encontros.size, Modifier.weight(1f))
        }

        Button(
            onClick = onCadastrarPet,
            colors = ButtonDefaults.buttonColors(containerColor = Cores.Teal),
            modifier = Modifier.fillMaxWidth()
        ) { Text("Cadastrar pet") }

        OutlinedButton(
            onClick = onCadastrarOng,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Cadastrar ONG") }
    }
}

@Composable
private fun Numero(rotulo: String, valor: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("$valor", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Cores.Teal)
            Text(rotulo, fontSize = 11.sp, color = Cores.Cinza)
        }
    }
}
