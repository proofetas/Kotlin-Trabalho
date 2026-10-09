package com.example.myapplication

// TelaFormPet.kt — formulário para ADICIONAR um pet à lista.
// Campos usados (seção 3.3 do trabalho):
//   TextField (nome, raça) | campo NUMÉRICO (idade) | MÚLTIPLAS LINHAS (descrição)
//   Checkbox (vacinado)    | RadioButton (escolher a ONG)
// Cada campo tem seu próprio estado (remember + mutableStateOf).

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TelaFormPet(viewModel: MeuViewModel, onVoltar: () -> Unit) {

    var nome by remember { mutableStateOf("") }
    var raca by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var vacinado by remember { mutableStateOf(false) }
    var ongEscolhida by remember { mutableStateOf<Int?>(viewModel.ongs.firstOrNull()?.id) }

    val context = LocalContext.current

    Scaffold(
        containerColor = Cores.Fundo,
        topBar = { BarraTopo(titulo = "Novo pet", onVoltar = onVoltar) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = raca,
                onValueChange = { raca = it },
                label = { Text("Raça") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Campo NUMÉRICO: teclado de números + só aceita dígitos
            OutlinedTextField(
                value = idade,
                onValueChange = { novo -> idade = novo.filter { it.isDigit() } },
                label = { Text("Idade (em meses)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Campo de MÚLTIPLAS LINHAS
            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = vacinado, onCheckedChange = { vacinado = it })
                Text("Pet vacinado")
            }

            Text("ONG responsável", fontWeight = FontWeight.Bold)

            if (viewModel.ongs.isEmpty()) {
                Text("Cadastre uma ONG primeiro (aba ONG ou Perfil).", color = Cores.Cinza)
            }

            // Uma linha com RadioButton para cada ONG da lista
            viewModel.ongs.forEach { ong ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = ongEscolhida == ong.id,
                        onClick = { ongEscolhida = ong.id }
                    )
                    Text(ong.nome)
                }
            }

            Button(
                onClick = {
                    val meses = idade.toIntOrNull()
                    val ongId = ongEscolhida

                    // Validação: não deixa salvar com campo vazio
                    if (nome.isBlank() || raca.isBlank() || meses == null || ongId == null) {
                        Toast.makeText(context, "Preencha nome, raça, idade e escolha uma ONG", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.adicionarPet(
                            nome = nome.trim(),
                            raca = raca.trim(),
                            idadeMeses = meses,
                            descricao = descricao.trim().ifBlank { "Aguardando uma família." },
                            ongId = ongId,
                            vacinado = vacinado
                        )
                        Toast.makeText(context, "$nome adicionado!", Toast.LENGTH_SHORT).show()
                        onVoltar()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Cores.Teal),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar pet")
            }
        }
    }
}

// ── Preview ──────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaFormPetPreview() {
    MaterialTheme {
        TelaFormPet(viewModel = viewModel(), onVoltar = {})
    }
}
