# Roteiro do vídeo — App Receitas

Ideia central para repetir ao longo da apresentação: **nenhum componente foi colocado só para
cumprir o enunciado**. O app é um catálogo de receitas com três etapas naturais — escolher o que
ver, navegar por uma lista, abrir o detalhe — e cada requisito caiu na etapa onde ele é a
ferramenta certa.

\---

## Integrante 1 — Visão geral, telas e Material Design

### O que mostrar

App rodando: Home → clique numa categoria → Lista → clique numa receita → Detalhe. Depois volte
para a Home e mostre o contador de favoritas atualizado.

### O que falar

**Por que esse tema.** Um app de receitas tem, por natureza, um dado hierárquico: categoria →
receita → detalhe da receita. Isso dá três telas com papéis distintos, sem precisar inventar
telas artificiais. É exatamente por isso que o tema foi escolhido: os requisitos do trabalho
encaixam no fluxo real do app, não o contrário.

**Por que não tem login.** O app não guarda dado de usuário nem acessa servidor; os favoritos são
locais e temporários. Uma tela de login seria uma tela a mais sem função, e o próprio enunciado
valoriza que cada recurso tenha finalidade real.

**Por que Material Design (requisito 8).** O Material 3 dá um sistema de cores e tipografia único
para o app todo. Isso importa aqui por um motivo técnico específico: temos telas em XML e uma
tela em Compose. Definimos a mesma paleta nos dois lugares — `themes.xml` para as telas XML e
`ui/theme/Theme.kt` para a tela Compose — então o usuário não percebe que trocou de tecnologia ao
abrir o detalhe. Usamos MaterialToolbar, MaterialCardView, MaterialButton e TextInputLayout em vez
dos componentes crus do Android porque eles já trazem elevação, cantos arredondados, estados de
toque e acessibilidade prontos, sem CSS manual.

\---

## Integrante 2 — Arquitetura MVVM

### O que mostrar

`model/Modelos.kt`, `data/ReceitaRepository.kt`, e as três ViewModels. Depois mostre que nenhuma
Activity chama `ReceitaRepository` diretamente.

### O que falar

**Por que MVVM e não tudo na Activity.** A Activity é destruída e recriada em situações comuns —
girar a tela, por exemplo. Se o estado morasse nela, o número de porções escolhido pelo usuário
se perderia. A ViewModel sobrevive a essa recriação, então o estado fica preservado. Esse é o
motivo prático de existir a separação, além da organização do código.

**Por que cada camada está onde está.**

* **Model** (`Receita`, `Ingrediente`, `Categoria`): classes de dados puras, sem nenhum import do
Android. Isso permite testar e reaproveitar a lógica fora da interface.
* **Repository**: fonte única de dados. Colocamos os favoritos aqui porque duas telas diferentes
precisam da mesma informação — a Home mostra o total e o Detalhe marca/desmarca. Se cada tela
guardasse sua própria lista, elas ficariam dessincronizadas.
* **ViewModel**: guarda o estado e decide *o que* mostrar. Exemplo concreto: quem valida se a busca
tem pelo menos 3 letras é a `MainViewModel`, não o botão. A Activity só pergunta o resultado e
exibe o erro.
* **View**: só infla layout, observa e reage.

**Por que LiveData em duas telas e StateFlow na terceira.** Não é inconsistência, é adequação.
LiveData é ciente do ciclo de vida da Activity e funciona muito bem com `observe()` nas telas XML.
Já o Compose trabalha com estado reativo e recomposição, e o `StateFlow` + `collectAsStateWithLifecycle()`
é o padrão recomendado nesse contexto. Aproveitamos o trabalho para demonstrar as duas abordagens.

**Por que `DetalheUiState` é uma data class única.** A tela de detalhe tem quatro informações que
mudam juntas (receita, porções, favorito, ingredientes recalculados). Agrupar em um único estado
evita que a tela apareça num estado inconsistente — por exemplo, porções já atualizadas mas
ingredientes ainda antigos.

\---

## Integrante 3 — Navegação e passagem de parâmetros por Intent

### O que mostrar

`MainActivity.abrirLista()` → `ListaReceitasActivity.onCreate()` (leitura do extra) →
`configurarCliqueDaLista()` → `DetalheReceitaActivity.onCreate()`. Rode o app em paralelo.

### O que falar

**Por que Intent com extras e não uma variável global.** As telas são Activities independentes; o
Android pode destruir e recriar qualquer uma delas. A Intent é o canal oficial de comunicação
entre elas e o sistema a restaura junto com a Activity. Uma variável estática sobreviveria por
acaso, não por garantia.

**Por que dois pontos de passagem diferentes.**

1. **Home → Lista**: mandamos `EXTRA\\\_CATEGORIA` ou `EXTRA\\\_BUSCA`. A tela de lista é a mesma nos dois
casos — só o filtro muda. Em vez de criar duas Activities quase idênticas, criamos uma tela
parametrizada. O parâmetro é exatamente o que define o conteúdo dela.
2. **Lista → Detalhe**: mandamos `EXTRA\\\_RECEITA\\\_ID`, um inteiro.

**Por que mandamos o id e não o objeto Receita inteiro.** Enviar o objeto exigiria torná-lo
`Parcelable` e duplicaria o dado: teríamos uma cópia na Intent e a original no repositório, que
podem divergir. Mandando só o id, a tela de destino busca a versão atual no repositório. É mais
leve e mantém a fonte única de verdade.

**Por que mandamos também o nome da receita.** É um detalhe de experiência: o nome aparece na barra
de título instantaneamente, antes mesmo de a ViewModel terminar de carregar a receita. Evita a
barra piscar vazia.

**Onde o valor recebido é usado.** Mostre que `intent.getStringExtra(...)` alimenta
`viewModel.carregar(categoria, busca)`, e que é esse valor que decide o título da tela e a lista
exibida. O parâmetro não é decorativo: sem ele, a tela não sabe o que mostrar.

\---

## Integrante 4 — View Binding, findViewById, componentes e layouts

### O que mostrar

`MainActivity` + `CategoriaAdapter` (binding), depois `ListaReceitasActivity` + `ReceitaAdapter`
(findViewById). Depois os quatro layouts XML.

### O que falar

**Por que as duas formas estão em telas diferentes.** Fizemos de propósito para comparar. O
`findViewById` devolve a view a partir de um id em tempo de execução: se o id estiver errado ou o
componente não existir naquele layout, o erro só aparece com o app rodando, em forma de crash. O
View Binding gera uma classe a partir do XML (`ActivityMainBinding`), então o erro vira erro de
compilação e o tipo da view já vem correto, sem cast.

**Por que a Home usa View Binding.** É a tela com mais componentes (toolbar, subtítulo, campo,
botão, grid, dois textos de rodapé). É onde o ganho de segurança e de código é maior — seriam sete
`findViewById` seguidos.

**Por que a lista usa findViewById.** Além de atender ao requisito, é a tela mais enxuta: cinco
views. Serve para demonstrar a forma tradicional em um caso onde ela ainda é aceitável.

**Por que GridView para categorias e ListView para receitas.** São poucas categorias, com nome
curto e emoji — conteúdo que fica bem lado a lado, em duas colunas, e permite ver todas de relance.
Já as receitas têm nome longo e uma linha de informação embaixo; em grade o texto quebraria e
ficaria ilegível. Além disso a lista é filtrada e pode ter muitos itens, o que pede rolagem
vertical. A escolha veio do formato do conteúdo, não do requisito.

**Por que EditText e Button juntos.** O campo de busca (`TextInputEditText`, que é uma subclasse de
`EditText` com visual Material) recebe o termo, e o botão dispara a ação já validada pela ViewModel.
Se o texto tiver menos de 3 letras, o próprio `TextInputLayout` mostra a mensagem de erro — é o
componente Material fazendo o trabalho de feedback.

**Por que os TextView têm função.** O da Home mostra o total de receitas e o de favoritas; o da
lista mostra o título dinâmico ("Categoria: Doces" ou "Resultados para ...") e a quantidade
encontrada. Nenhum é rótulo morto: todos exibem dado vindo da ViewModel.

**Por que ConstraintLayout na Home e LinearLayout na lista.** A Home tem elementos ancorados no
topo, no rodapé e um grid que ocupa o espaço restante — relações entre irmãos que o
ConstraintLayout resolve numa hierarquia plana, sem aninhar layouts. A tela de lista é
simplesmente uma pilha vertical (barra, título, contador, lista); LinearLayout com `layout\\\_weight`
faz isso com menos código. Nos itens é o mesmo raciocínio: o card de categoria é uma coluna
centralizada (LinearLayout) e o card de receita alinha emoji, nome, info e estrela em posições
relativas (ConstraintLayout).

\---

## Integrante 5 — Jetpack Compose e as funções Kotlin

### O que mostrar

`DetalheReceitaActivity.kt` e `util/ReceitaUtils.kt`. No app, aumente as porções e mostre os
ingredientes mudando; favorite e volte para a Home.

### O que falar

**Por que o detalhe foi a tela escolhida para Compose.** É a única tela que muda sozinha enquanto o
usuário interage: mexer nas porções recalcula todos os ingredientes, favoritar troca ícone e texto
do botão. Em XML isso exigiria percorrer as views e atualizar cada uma na mão. No Compose, a
interface é função do estado: quando o `DetalheUiState` muda, a tela se redesenha sozinha. Ou seja,
escolhemos a tela onde o modelo declarativo do Compose resolve um problema real — não uma tela
qualquer só para cumprir o item.

**Por que ela está integrada ao app.** Recebe o id pela Intent, usa a mesma ViewModel/Repository das
outras telas e grava o favorito que aparece de volta no contador da Home. Se removêssemos essa tela,
o fluxo quebraria.

**Por que as funções foram escritas assim (requisito 1).**

* `ajustarQuantidade(quantidadeOriginal, porcoesOriginais, porcoesDesejadas)`: é a regra central do
app — receita escrita para 4 porções e o usuário quer fazer para 6. Recebe os três valores e
devolve a quantidade proporcional. Tem uma guarda para porções zero, para não dividir por zero.
* `formatarQuantidade(valor)`: separada da anterior de propósito. Uma função calcula, a outra
apresenta. Existe porque "2 ovos" é melhor que "2.0 ovos" — é ajuste de exibição, não de cálculo.
* `descreverIngrediente(...)`: junta as duas e devolve a linha pronta. Assim a tela não precisa
saber montar texto.
* `classificarTempo(tempoMinutos)`: transforma um número em informação útil ("Rápida", "Média",
"Demorada"). É o tipo de processamento que ajuda quem está decidindo o que cozinhar.
* `buscaEhValida(texto)`: evita buscar com uma ou duas letras, que retornaria quase tudo.

**Por que essas funções ficam em `ReceitaUtils` e não dentro da tela.** Elas são usadas em lugares
diferentes — `classificarTempo` aparece no card da lista e no chip do detalhe, `resumoDaReceita` no
adapter. Centralizar evita repetir a regra e garante que ela mude num lugar só. E como são funções
puras (mesma entrada, mesma saída, sem estado), são fáceis de testar e de explicar.

\---

## Fechamento (qualquer integrante)

Resumo de uma frase: as três telas existem porque o domínio tem três níveis; cada componente foi
escolhido pelo formato do conteúdo que ele exibe; e MVVM está lá para que estado e regra sobrevivam
ao ciclo de vida das Activities. Os requisitos do enunciado foram atendidos dentro dessas decisões,
não por cima delas.

**Lembrete:** cada integrante deve dizer o nome completo antes de começar a sua parte.

