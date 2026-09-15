##  Mapeamento dos Requisitos

### Requisito 1: Funções Kotlin com Parâmetro e Retorno
Funções escritas pelo grupo que recebem dados de entrada, realizam processamento e retornam resultados utilizados na interface.
* **`ReceitaUtils.buscaEhValida(texto)`** em `br.com.receitasapp.util.ReceitaUtils.kt`
* **`ReceitaRepository.buscarPorTexto(texto)`** em `br.com.receitasapp.data.ReceitaRepository.kt`
* **`ReceitaRepository.listarPorCategoria(categoria)`** em `br.com.receitasapp.data.ReceitaRepository.kt`
* **`ReceitaRepository.listarCategorias()`** em `br.com.receitasapp.data.ReceitaRepository.kt`
* **`ReceitaRepository.alternarFavorita(id)`** em `br.com.receitasapp.data.ReceitaRepository.kt`
* **`DetalheReceitaViewModel.formatarIngredientes(...)`** em `br.com.receitasapp.viewmodel.DetalheReceitaViewModel.kt`

### Requisito 2: Arquitetura MVVM (Model-View-ViewModel)
Camadas totalmente isoladas. As Views apenas observam os LiveData/StateFlow expostos pelas ViewModels; toda regra de negócio fica nas ViewModels e Repositórios.
* **Model**: `br.com.receitasapp.model.Modelos.kt` e `br.com.receitasapp.data.ReceitaRepository.kt`
* **Home**: `MainActivity.kt` observa `MainViewModel.kt`
* **Lista**: `ListaReceitasActivity.kt` observa `ListaReceitasViewModel.kt`
* **Cadastro**: `CadastroActivity.kt` observa `CadastroViewModel.kt`
* **Detalhes**: `DetalheReceitaActivity.kt` observa `DetalheReceitaViewModel.kt`

### Requisito 3: Navegação entre Telas com Passagem de Parâmetros
Fluxo completo de navegação e transferência de dados entre atividades por meio de Intents.
* **MainActivity ➔ ListaReceitasActivity**: Passagem da categoria ou termo pesquisado (`EXTRA_CATEGORIA`, `EXTRA_BUSCA`).
* **ListaReceitasActivity ➔ DetalheReceitaActivity**: Passagem do ID e nome da receita para carregamento em Compose (`EXTRA_RECEITA_ID`).

### Requisito 4: findViewById e View Binding
Uso consciente e demonstrativo das duas abordagens de vinculação de views no Android.
* **View Binding Habilitado**: Configurado no `app/build.gradle`.
* **Uso do View Binding**: `MainActivity.kt` (usando `ActivityMainBinding`) e `CadastroActivity.kt` (usando `ActivityCadastroBinding`).
* **Uso do findViewById**: `ListaReceitasActivity.kt` e `ReceitaAdapter.kt`.

### Requisito 5: Componentes Visuais Obrigatórios (TextView, EditText, Button, GridView, ListView)
Uso correto dos elementos visuais clássicos de interface gráfica.
* **TextView, EditText, Button, GridView**: Declarados e implementados em `activity_main.xml`.
* **TextView, ListView**: Declarados e implementados em `activity_lista_receitas.xml`.

### Requisito 6: Gerenciadores de Layout (ConstraintLayout e LinearLayout)
Uso estruturado e otimizado de layouts nativos.
* **ConstraintLayout**: Raiz de `activity_main.xml` e no item interno `item_receita.xml`.
* **LinearLayout**: Raiz de `activity_lista_receitas.xml` e no item interno `item_categoria.xml`.

### Requisito 7: Jetpack Compose
Interface declarativa moderna e reativa usada de ponta a ponta em uma das telas.
* **Habilitado**: Configurado no `app/build.gradle`.
* **Implementação**: `DetalheReceitaActivity.kt` construída inteiramente com componentes Compose.

### Requisito 8: Material Design 3 e Temas
Customização visual moderna baseada na paleta de cores e componentes estruturais do Material 3.
* **Temas e Cores XML**: Configurado em `res/values/themes.xml` e `res/values/colors.xml`.
* **Componentes Compose M3**: Uso de `TopAppBar`, `Card`, `Chip` e `Button` na tela de detalhes.
