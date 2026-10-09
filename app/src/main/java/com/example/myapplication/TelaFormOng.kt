package com.example.myapplication

// TelaFormOng.kt — formulário para ADICIONAR uma ONG à lista.

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TelaFormOng(viewModel: MeuViewModel, onVoltar: () -> Unit) {

    var nome by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        containerColor = Cores.Fundo,
        topBar = { BarraTopo(titulo = "Nova ONG", onVoltar = onVoltar) }
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
                label = { Text("Nome da ONG") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = cidade,
                onValueChange = { cidade = it },
                label = { Text("Cidade / UF") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text("Telefone") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    if (nome.isBlank() || cidade.isBlank()) {
                        Toast.makeText(context, "Preencha nome e cidade", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.adicionarOng(
                            nome = nome.trim(),
                            cidade = cidade.trim(),
                            telefone = telefone.trim(),
                            descricao = descricao.trim().ifBlank { "Sem descrição." }
                        )
                        Toast.makeText(context, "$nome adicionada!", Toast.LENGTH_SHORT).show()
                        onVoltar()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Cores.Teal),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar ONG")
            }
        }
    }
}

// ── Preview ──────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaFormOngPreview() {
    MaterialTheme {
        TelaFormOng(viewModel = viewModel(), onVoltar = {})
    }
}
