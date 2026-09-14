package br.com.receitasapp.viewmodel

import androidx.lifecycle.ViewModel
import br.com.receitasapp.data.ReceitaRepository
import br.com.receitasapp.model.Receita
import br.com.receitasapp.util.ReceitaUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Estado observado pela tela feita em Jetpack Compose.
 */
data class DetalheUiState(
    val receita: Receita? = null,
    val porcoes: Int = 1,
    val favorita: Boolean = false,
    val ingredientesAjustados: List<String> = emptyList()
)

/**
 * CAMADA VIEWMODEL (Requisito 2 - MVVM) da tela de detalhe em Compose.
 *
 * Guarda o estado da tela (receita, porcoes e favorito) e recalcula os
 * ingredientes sempre que o usuario muda o numero de porcoes.
 */
class DetalheReceitaViewModel : ViewModel() {

    private val _estado = MutableStateFlow(DetalheUiState())
    val estado: StateFlow<DetalheUiState> = _estado.asStateFlow()

    fun carregar(receitaId: Int) {
        val receita = ReceitaRepository.buscarPorId(receitaId) ?: return
        _estado.value = DetalheUiState(
            receita = receita,
            porcoes = receita.porcoesBase,
            favorita = ReceitaRepository.ehFavorita(receita.id),
            ingredientesAjustados = calcularIngredientes(receita, receita.porcoesBase)
        )
    }

    /**
     * Requisito 1: recebe parametros e retorna a lista de textos ja convertida.
     */
    private fun calcularIngredientes(receita: Receita, porcoes: Int): List<String> =
        receita.ingredientes.map {
            ReceitaUtils.descreverIngrediente(it, receita.porcoesBase, porcoes)
        }

    fun alterarPorcoes(delta: Int) {
        val atual = _estado.value
        val receita = atual.receita ?: return
        val novasPorcoes = (atual.porcoes + delta).coerceIn(1, 20)
        _estado.value = atual.copy(
            porcoes = novasPorcoes,
            ingredientesAjustados = calcularIngredientes(receita, novasPorcoes)
        )
    }

    fun alternarFavorita() {
        val receita = _estado.value.receita ?: return
        val novoEstado = ReceitaRepository.alternarFavorita(receita.id)
        _estado.value = _estado.value.copy(favorita = novoEstado)
    }
}
