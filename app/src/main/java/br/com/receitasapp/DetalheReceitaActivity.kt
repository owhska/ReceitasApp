package br.com.receitasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.receitasapp.model.Receita
import br.com.receitasapp.ui.theme.ReceitasTheme
import br.com.receitasapp.util.ReceitaUtils
import br.com.receitasapp.viewmodel.DetalheReceitaViewModel
import br.com.receitasapp.viewmodel.DetalheUiState

/**
 * TELA 3 - DETALHE DA RECEITA.
 *
 * REQUISITO 7: esta tela e construida inteiramente com JETPACK COMPOSE.
 * REQUISITO 3: recebe o id da receita por Intent e usa esse valor para carregar os dados.
 * REQUISITO 2: continua usando MVVM (DetalheReceitaViewModel + StateFlow).
 * REQUISITO 8: componentes Material Design 3 (TopAppBar, Card, Chip, Button).
 */
class DetalheReceitaActivity : ComponentActivity() {

    companion object {
        const val EXTRA_RECEITA_ID = "extra_receita_id"
        const val EXTRA_RECEITA_NOME = "extra_receita_nome"
    }

    private val viewModel: DetalheReceitaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // REQUISITO 3 - valor recebido pela Intent e utilizado nesta tela
        val receitaId = intent.getIntExtra(EXTRA_RECEITA_ID, -1)
        val nomeRecebido = intent.getStringExtra(EXTRA_RECEITA_NOME).orEmpty()
        viewModel.carregar(receitaId)

        setContent {
            ReceitasTheme {
                val estado by viewModel.estado.collectAsStateWithLifecycle()
                DetalheReceitaScreen(
                    estado = estado,
                    tituloRecebido = nomeRecebido,
                    onVoltar = { finish() },
                    onFavoritar = { viewModel.alternarFavorita() },
                    onAlterarPorcoes = { delta -> viewModel.alterarPorcoes(delta) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheReceitaScreen(
    estado: DetalheUiState,
    tituloRecebido: String,
    onVoltar: () -> Unit,
    onFavoritar: () -> Unit,
    onAlterarPorcoes: (Int) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text(text = estado.receita?.nome ?: tituloRecebido) },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->

        val receita = estado.receita
        if (receita == null) {
            Surface(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                Text(
                    text = "Receita nao encontrada.",
                    modifier = Modifier.padding(24.dp)
                )
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                CabecalhoReceita(receita = receita)
                Spacer(modifier = Modifier.height(16.dp))
                SeletorDePorcoes(
                    porcoes = estado.porcoes,
                    onAlterarPorcoes = onAlterarPorcoes
                )
                Spacer(modifier = Modifier.height(16.dp))
                BotaoFavorito(favorita = estado.favorita, onFavoritar = onFavoritar)
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Ingredientes",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(estado.ingredientesAjustados) { linha ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Text(
                        text = linha,
                        modifier = Modifier.padding(12.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Modo de preparo",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            itemsIndexed(receita.modoPreparo) { indice, passo ->
                Row(modifier = Modifier.padding(vertical = 6.dp)) {
                    Text(
                        text = "${indice + 1}.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = passo, style = MaterialTheme.typography.bodyLarge)
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
private fun CabecalhoReceita(receita: Receita) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = receita.nome,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = { }, label = { Text(receita.categoria) })
                // Requisito 1: funcao Kotlin com parametro e retorno
                AssistChip(
                    onClick = { },
                    label = { Text(ReceitaUtils.classificarTempo(receita.tempoPreparoMin)) }
                )
                AssistChip(onClick = { }, label = { Text("${receita.tempoPreparoMin} min") })
            }
        }
    }
}

@Composable
private fun SeletorDePorcoes(porcoes: Int, onAlterarPorcoes: (Int) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Porcoes", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "As quantidades sao recalculadas",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedIconButton(onClick = { onAlterarPorcoes(-1) }) {
                    Icon(Icons.Filled.Remove, contentDescription = "Diminuir porcoes")
                }
                Text(
                    text = porcoes.toString(),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                OutlinedIconButton(onClick = { onAlterarPorcoes(1) }) {
                    Icon(Icons.Filled.Add, contentDescription = "Aumentar porcoes")
                }
            }
        }
    }
}

@Composable
private fun BotaoFavorito(favorita: Boolean, onFavoritar: () -> Unit) {
    FilledTonalButton(
        onClick = onFavoritar,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = if (favorita) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = if (favorita) "Remover dos favoritos" else "Adicionar aos favoritos")
    }
}
