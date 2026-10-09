# Adote Seu Amigo — Trabalho 2 (MAF)

App Android em Kotlin + Jetpack Compose para adoção de pets, feito para o Trabalho 2 (Mínimo Aplicativo Funcional). Continua o Trabalho 1: as 3 telas estilizadas agora têm navegação real, listas dinâmicas, formulários e telas de detalhes.

**Integrantes:** _(nomes do trio)_

---

## Sumário

1. [Visão geral](#1-visão-geral)
2. [Como rodar](#2-como-rodar)
3. [Mapa de telas e navegação](#3-mapa-de-telas-e-navegação)
4. [O app em funcionamento (tela por tela)](#4-o-app-em-funcionamento-tela-por-tela)
5. [Estrutura do código](#5-estrutura-do-código)
6. [Documentação do processo](#6-documentação-do-processo)
7. [Atendimento aos requisitos do trabalho](#7-atendimento-aos-requisitos-do-trabalho)
8. [Limitações](#8-limitações)

---

## 1. Visão geral

O **Adote Seu Amigo** conecta pessoas a pets de ONGs. O usuário navega pelos pets, favorita os que quer conhecer, agenda um encontro, vê as ONGs e as campanhas de adoção, e pode cadastrar novos pets e ONGs.

- **9 telas** navegáveis, todas com propósito.
- **2 data classes** principais: `Pet` e `Ong` (cada pet pertence a uma ONG).
- **2 listas** com `LazyColumn` + `Card` (pets na Home, ONGs na aba ONG), cada uma com adicionar e remover.
- **2 telas de detalhes**, uma por lista, recebendo o id pela rota.
- **BottomNavigation** com 5 áreas: HOME, PETS, ONG, ENCONTRO, PERFIL.

## 2. Como rodar

1. Clonar o repositório.
2. Abrir a pasta no **Android Studio**.
3. Esperar o **Gradle Sync** terminar.
4. Escolher um emulador (ou celular com depuração USB) e clicar em **Run ▶**.

Requisitos: Android Studio recente e minSdk 24.

## 3. Mapa de telas e navegação

```
MainActivity
 └─ AppNavigation  (NavHost principal + MeuViewModel)
     ├─ "home"        → TelaComAbas  (TopAppBar + BottomNavigation + NavHost interno)
     │                   ├─ aba_home      → AbaHome      (lista de pets)
     │                   ├─ aba_pets      → AbaPets      (grade de pets)
     │                   ├─ aba_ong       → AbaOng       (campanhas + lista de ONGs)
     │                   ├─ aba_encontro  → AbaEncontro
     │                   └─ aba_perfil    → AbaPerfil
     ├─ "pet/{petId}" → TelaDetalhesPet
     ├─ "ong/{ongId}" → TelaDetalhesOng
     ├─ "form_pet"    → TelaFormPet
     └─ "form_ong"    → TelaFormOng
```

Principais caminhos de navegação:

- Home, Pets ou Encontro → toque num pet → **Detalhes do Pet**.
- Detalhes do Pet → cartão da ONG → **Detalhes da ONG**.
- Detalhes da ONG → toque num pet da ONG → **Detalhes do Pet**.
- Aba ONG → toque numa ONG → **Detalhes da ONG**.
- Botão "+" na Home → **Novo Pet**. Botão "+" na aba ONG → **Nova ONG**.
- Perfil → "Cadastrar pet" / "Cadastrar ONG" → formulários.
- Todas as telas de detalhe e formulário têm botão de **voltar** na barra superior.

## 4. O app em funcionamento (tela por tela)

> Os prints ficam na pasta `prints/`. Substituir cada marcador pela imagem real tirada no emulador.

### 4.1 Home — "Adote seu Amigo"


Barra superior verde com o título e campo de busca. Abaixo, uma lista de cards, um por pet: foto, nome, raça em destaque e uma descrição que termina com a cidade e a ONG responsável ("Localizado em Curitiba / PR — Patinhas do Sul").

- **Buscar:** digitar filtra a lista por nome ou raça, na hora.
- **Abrir:** tocar no card abre os Detalhes do Pet.
- **Remover:** tocar na lixeira, ou segurar o card (clique longo). Aparece um aviso "Removendo …" e o pet some da lista.
- **Adicionar:** o botão "+" no canto inferior abre o formulário de novo pet.

### 4.2 Pets — "Loucos para te conhecer"


Grade de duas colunas com foto, nome, raça e idade. Cada foto tem um selo circular (check) no canto.

- **Favoritar:** tocar no selo marca ou desmarca o pet como favorito. O selo fica verde quando marcado.
- Na barra superior, um coração com o **número de favoritos**, que atualiza na hora.
- Tocar no card abre os Detalhes do Pet.

### 4.3 ONG — "CCA Datão"


Parte de cima: quatro cards de **campanha de adoção**, com uma bolinha de data ao lado (verde com check quando confirmada, cinza quando não). Parte de baixo: a seção "Organização Não Governamental (ONG)" com a lista de ONGs.

- **Saiba mais:** tocar numa campanha expande a descrição; tocar de novo recolhe.
- **Abrir:** tocar numa ONG abre os Detalhes da ONG.
- **Remover:** lixeira ou clique longo. Remover uma ONG também remove os pets dela.
- **Adicionar:** o botão "+" abre o formulário de nova ONG.

### 4.4 Encontro

Lista os pets favoritados. Cada card tem um botão **Agendar**; depois de agendar, o card mostra "Encontro agendado ✓" e o botão vira **Cancelar**. Sem favoritos, a tela explica como marcar um pet na aba PETS.

### 4.5 Perfil


Quatro contadores calculados em tempo real: pets, ONGs, favoritos e encontros agendados. Dois atalhos: **Cadastrar pet** e **Cadastrar ONG**.

### 4.6 Detalhes do Pet
![Detalhes do Pet](prints/06-detalhes-pet.png)

Foto, nome, raça, etiquetas (idade, **fase da vida** e vacinado/não vacinado), descrição e o cartão da **ONG responsável**.

- A **fase da vida** (Filhote, Adulto ou Sênior) é calculada a partir da idade em meses.
- O cartão da ONG é clicável e leva aos Detalhes daquela ONG.
- O botão "Quero conhecer …" favorita o pet; depois vira "Nos meus favoritos".

### 4.7 Detalhes da ONG
![Detalhes da ONG](prints/07-detalhes-ong.png)

Cidade, telefone e descrição da ONG, a contagem "N pets esperando por um lar" e a lista dos pets que pertencem a ela. Tocar num pet abre os Detalhes dele. O botão "Remover ONG e seus pets" apaga a ONG e volta para a tela anterior.

### 4.8 Novo Pet
![Novo Pet](prints/08-form-pet.png)

Formulário com nome, raça, idade em meses (teclado numérico, só aceita dígitos), descrição (várias linhas), caixa "Pet vacinado" e seleção da ONG responsável. Se faltar algo, aparece um aviso e nada é salvo. Ao salvar, o pet aparece no topo da lista da Home e a tela volta sozinha.

### 4.9 Nova ONG
![Nova ONG](prints/09-form-ong.png)

Formulário com nome, cidade, telefone e descrição. Nome e cidade são obrigatórios. Ao salvar, a ONG aparece no topo da lista e fica disponível no formulário de novo pet.

## 5. Estrutura do código

| Arquivo | Função |
|---|---|
| `MainActivity.kt` | única Activity; chama `AppNavigation` |
| `AppNavigation.kt` | NavHost principal e criação do `MeuViewModel` |
| `Rotas.kt` | `object Rotas` (NavHost principal) e `object RotasAbas` (abas) |
| `TelaComAbas.kt` | TopAppBar, botão "+", BottomNavigation e NavHost interno das abas |
| `BottomNavBar.kt` | `NavigationBar` com as 5 abas |
| `Modelos.kt` | data classes `Pet`, `Ong` e `Campanha` |
| `MeuViewModel.kt` | listas reativas e funções de adicionar, remover e favoritar |
| `AbaHome`, `AbaPets`, `AbaOng`, `AbaEncontro`, `AbaPerfil` | telas das abas |
| `TelaDetalhesPet`, `TelaDetalhesOng` | telas de detalhes |
| `TelaFormPet`, `TelaFormOng` | formulários |
| `Componentes.kt` | foto do pet, barra superior com voltar, etiqueta |
| `Cores.kt` | paleta de cores do app |

## 6. Documentação do processo

> ⚠️ Completar com o texto do trio e com prints ou vídeo em cada etapa. Só texto, sem imagem do app funcionando, não vale.

### 6.1 Como estava o Trabalho 1 e o que mudou

No Trabalho 1 havia 3 telas estilizadas (Home, Pets e ONG). Os botões existiam e estavam estilizados, mas não trocavam de tela, e os dados eram fixos.

Agora o app tem navegação real, listas que crescem e diminuem, formulários e detalhes que mostram o item certo.

_(Acrescentar: print do Trabalho 1 e print equivalente de agora.)_

### 6.2 Por que essas telas novas

- **Detalhes do Pet e da ONG:** cada lista precisa abrir o item escolhido, e a ONG liga as duas listas.
- **Novo Pet e Nova ONG:** são o jeito de adicionar itens de verdade nas listas.
- **Encontro:** dá um objetivo ao app depois de escolher o pet, que é agendar uma visita.
- **Perfil:** mostra o resumo do uso e atalhos de cadastro.

_(Acrescentar o que o trio realmente discutiu e um print de cada tela.)_

### 6.3 Decisões de configuração e organização

- Rotas em `object Rotas` e `object RotasAbas`, para evitar erro de digitação.
- Um NavHost principal e um NavHost interno só para as abas, como visto em aula. A BottomNavigation aparece só nas abas e some nos detalhes e formulários, que abrem por cima.
- As telas recebem lambdas (`onVoltar`, `onPetClick`) em vez do `navController`.
- Um único `MeuViewModel`, criado no `AppNavigation` e passado às telas. Adicionar ou remover numa tela aparece em todas.
- O id do item vai pela rota (`pet/{petId}`) e a tela busca o item no ViewModel.
- Cada pet guarda o `ongId` da ONG, o que permite cruzar as duas listas.

_(Acrescentar outras decisões reais do trio.)_

### 6.4 Complexidade extra na tela de Detalhes

- **Detalhes do Pet:** fase da vida calculada pela idade, cartão da ONG responsável com navegação para os detalhes dela, e botão que altera o favorito do próprio item.
- **Detalhes da ONG:** lista os pets daquela ONG a partir da lista de pets, conta quantos são e permite abrir o detalhe de cada um. Também remove a ONG junto com seus pets.

_(Acrescentar por que o trio escolheu justamente isso.)_

### 6.5 Dificuldades e como resolvemos

_(Quem teve dificuldade, em quê, e como resolveram. Exemplos de pontos que costumam dar trabalho: passar o id pela rota, usar dois NavControllers, fazer a lista atualizar ao adicionar ou remover, sincronizar o Gradle.)_

## 7. Atendimento aos requisitos do trabalho

- [x] Novo repositório com README explicando como rodar
- [x] Mínimo de 7 telas navegáveis, todas com propósito (são 9)
- [x] Um NavHost central controlando as telas (`AppNavigation.kt`)
- [x] Objeto `Rotas` com rotas como `const val String`
- [x] `BottomNavigation` funcionando, com 5 áreas
- [x] Botões de navegação com `navController.navigate(...)`
- [x] `TopAppBar` com botão de voltar funcional (`popBackStack()`)
- [x] 2 data classes diferentes (`Pet` e `Ong`)
- [x] 2 telas de lista com `LazyColumn` + `Card` e `mutableStateListOf`
- [x] Em cada lista, adicionar pela UI e remover pela UI
- [x] 2 telas de detalhes, cada uma mostrando o item certo
- [x] Pelo menos uma tela de detalhes com algo além do exemplo de aula
- [x] Variedade de componentes: `TextField`, campo numérico, múltiplas linhas, `Checkbox`, `RadioButton`, `combinedClickable`
- [ ] Documentação com prints ou vídeo (preencher a seção 6)
- [ ] Testar cada botão antes de subir no GitHub

## 8. Limitações

Os dados ficam só na memória. Ao fechar o app, o que foi adicionado, removido ou favoritado se perde. Guardar os dados para sobreviver ao fechamento do app fica para o próximo trabalho, como o enunciado prevê.
