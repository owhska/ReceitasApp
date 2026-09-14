# Receitas - Trabalho de Desenvolvimento Mobile II

Aplicativo Android em Kotlin com catalogo de receitas, sem tela de login.

## Como abrir

1. Android Studio > **Open** > selecione a pasta `ReceitasApp`.
2. Aguarde o Gradle Sync (na primeira vez o Android Studio baixa o Gradle 8.7 e as dependencias).
3. Se aparecer aviso de wrapper ausente, aceite a sugestao do Android Studio ou rode
   `gradle wrapper` / use o botao **Sync Project with Gradle Files**.
4. Rode em um emulador ou celular com Android 7.0 (API 24) ou superior.

Versoes usadas: AGP 8.5.2, Kotlin 1.9.24, compileSdk 34, JDK 17.

## Telas

| # | Tela | Construida com | Funcao |
|---|------|----------------|--------|
| 1 | `MainActivity` | XML + **ConstraintLayout** + **View Binding** | Busca e **GridView** de categorias |
| 2 | `ListaReceitasActivity` | XML + **LinearLayout** + **findViewById** | **ListView** com as receitas filtradas |
| 3 | `DetalheReceitaActivity` | **Jetpack Compose** + Material 3 | Detalhe, ajuste de porcoes e favoritar |

Fluxo: Home -> (clique na categoria **ou** busca) -> Lista -> (clique na receita) -> Detalhe em Compose.
A tela Compose grava o favorito no repositorio, e o contador de favoritas aparece de volta na Home,
ou seja, ela esta integrada ao restante do sistema (nao e uma tela demonstrativa).

## Onde cada requisito foi implementado (roteiro do video)

### 1. Kotlin - funcoes com parametros e retorno
- `util/ReceitaUtils.kt`:
  - `ajustarQuantidade(quantidadeOriginal, porcoesOriginais, porcoesDesejadas): Double`
  - `descreverIngrediente(ingrediente, porcoesOriginais, porcoesDesejadas): String`
  - `classificarTempo(tempoMinutos): String`
  - `resumoDaReceita(receita): String`
  - `buscaEhValida(texto): Boolean`
- `data/ReceitaRepository.kt`: `listarPorCategoria(categoria)`, `buscarPorTexto(texto)`,
  `listarCategorias()`, `alternarFavorita(id)`.
- **Demonstracao ao vivo:** na tela de detalhe, aumente/diminua as porcoes e mostre os
  ingredientes sendo recalculados por `ajustarQuantidade`.

### 2. Arquitetura MVVM
- **Model:** `model/Modelos.kt` (Receita, Ingrediente, Categoria) + `data/ReceitaRepository.kt`.
- **View:** `MainActivity`, `ListaReceitasActivity`, `DetalheReceitaActivity` (+ layouts XML e composables).
- **ViewModel:** `viewmodel/MainViewModel.kt` (LiveData), `viewmodel/ListaReceitasViewModel.kt` (LiveData),
  `viewmodel/DetalheReceitaViewModel.kt` (StateFlow + `DetalheUiState`).
- Nenhuma Activity acessa o repositorio direto: elas so observam a ViewModel.

### 3. Navegacao + passagem de parametros por Intent
- `MainActivity.abrirLista()` envia `EXTRA_CATEGORIA` e `EXTRA_BUSCA`.
- `ListaReceitasActivity.onCreate()` le com `intent.getStringExtra(...)` e passa para
  `viewModel.carregar(categoria, busca)` -> o valor recebido define a lista exibida.
- `ListaReceitasActivity.configurarCliqueDaLista()` envia `EXTRA_RECEITA_ID`;
  `DetalheReceitaActivity.onCreate()` le com `intent.getIntExtra(...)` e carrega a receita.

### 4. findViewById e View Binding
- **View Binding:** `MainActivity` (`ActivityMainBinding`) e `adapter/CategoriaAdapter.kt`
  (`ItemCategoriaBinding`). Habilitado em `app/build.gradle` (`buildFeatures { viewBinding true }`).
- **findViewById:** `ListaReceitasActivity` e `adapter/ReceitaAdapter.kt`.

### 5. Componentes de interface
- **TextView:** titulo/subtitulo/resumo da Home, titulo e contador da lista, nome e info dos itens.
- **EditText:** `edtBusca` (TextInputEditText, subclasse de EditText, com estilo Material).
- **Button:** `btnBuscar` (MaterialButton) - dispara a busca validada pela ViewModel.
- **ListView:** `listReceitas` - lista de receitas, com clique abrindo o detalhe.
- **GridView:** `gridCategorias` - categorias em 2 colunas, com clique filtrando as receitas.

### 6. Gerenciadores de layout
- **ConstraintLayout:** `activity_main.xml` (raiz) e `item_receita.xml`.
- **LinearLayout:** `activity_lista_receitas.xml` (raiz) e `item_categoria.xml`.

### 7. Jetpack Compose
- `DetalheReceitaActivity.kt` - `setContent { ReceitasTheme { DetalheReceitaScreen(...) } }`.
- Composables: `DetalheReceitaScreen`, `CabecalhoReceita`, `SeletorDePorcoes`, `BotaoFavorito`.
- Integrada ao fluxo: recebe o id por Intent, usa a ViewModel e grava o favorito que a Home mostra.

### 8. Material Design
- Tema `Theme.ReceitasApp` (Material3) em `res/values/themes.xml` + paleta em `colors.xml`.
- Componentes: MaterialToolbar, MaterialCardView, MaterialButton, TextInputLayout.
- No Compose: `ReceitasTheme` (Material 3), Scaffold, TopAppBar, Card, AssistChip,
  FilledTonalButton, OutlinedIconButton, tipografia e cores do MaterialTheme.

## Sugestao de divisao da apresentacao (5 integrantes)

1. Visao geral do app + telas + Material Design.
2. MVVM: Model, Repository e as tres ViewModels.
3. Navegacao e passagem de parametros via Intent (Home -> Lista -> Detalhe).
4. View Binding x findViewById + componentes (TextView, EditText, Button, ListView, GridView)
   e os layouts (ConstraintLayout e LinearLayout).
5. Jetpack Compose e as funcoes Kotlin com parametro e retorno (`ReceitaUtils`).

Cada integrante deve se identificar com o nome completo antes de comecar a sua parte.
