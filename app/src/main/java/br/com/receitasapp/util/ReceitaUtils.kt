package br.com.receitasapp.util

import br.com.receitasapp.model.Ingrediente
import br.com.receitasapp.model.Receita
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * Funcoes Kotlin escritas pelo grupo que recebem parametros,
 * realizam um processamento ligado ao dominio do app e retornam um valor.
 */
object ReceitaUtils {

    /**
     * Recalcula a quantidade de um ingrediente para um numero diferente de porcoes.
     *
     * @param quantidadeOriginal quantidade prevista na receita
     * @param porcoesOriginais numero de porcoes da receita original
     * @param porcoesDesejadas numero de porcoes escolhido pelo usuario
     * @return a nova quantidade proporcional
     */
    fun ajustarQuantidade(
        quantidadeOriginal: Double,
        porcoesOriginais: Int,
        porcoesDesejadas: Int
    ): Double {
        if (porcoesOriginais <= 0) return quantidadeOriginal
        return quantidadeOriginal * porcoesDesejadas / porcoesOriginais
    }

    /**
     * Converte um numero em texto amigavel, escondendo casas decimais desnecessarias.
     *
     * @param valor quantidade calculada
     * @return o valor formatado ("2" em vez de "2.0", "1,5" em vez de "1.5")
     */
    fun formatarQuantidade(valor: Double): String {
        val arredondado = (valor * 100).roundToInt() / 100.0
        return if (abs(arredondado - arredondado.toInt()) < 0.001) {
            arredondado.toInt().toString()
        } else {
            String.format("%.2f", arredondado).trimEnd('0').trimEnd('.', ',').replace('.', ',')
        }
    }

    /**
     * Monta a linha de texto de um ingrediente ja convertida para as porcoes escolhidas.
     *
     * @param ingrediente ingrediente da receita
     * @param porcoesOriginais porcoes previstas na receita
     * @param porcoesDesejadas porcoes escolhidas na tela de detalhe
     * @return texto pronto para exibicao, por exemplo "3 unidades de Cenoura media"
     */
    fun descreverIngrediente(
        ingrediente: Ingrediente,
        porcoesOriginais: Int,
        porcoesDesejadas: Int
    ): String {
        val quantidade = ajustarQuantidade(ingrediente.quantidade, porcoesOriginais, porcoesDesejadas)
        return "${formatarQuantidade(quantidade)} ${ingrediente.unidade} de ${ingrediente.nome}"
    }

    /**
     * Classifica a receita de acordo com o tempo de preparo.
     *
     * @param tempoMinutos tempo de preparo em minutos
     * @return "Rapida", "Media" ou "Demorada"
     */
    fun classificarTempo(tempoMinutos: Int): String = when {
        tempoMinutos <= 20 -> "Rapida"
        tempoMinutos <= 45 -> "Media"
        else -> "Demorada"
    }

    /**
     * Gera o resumo mostrado no card de cada receita da lista.
     *
     * @param receita receita a ser descrita
     * @return texto com tempo, classificacao, porcoes e dificuldade
     */
    fun resumoDaReceita(receita: Receita): String {
        val classificacao = classificarTempo(receita.tempoPreparoMin)
        return "${receita.tempoPreparoMin} min - $classificacao - " +
            "${receita.porcoesBase} porcoes - ${receita.dificuldade}"
    }

    /**
     * Valida o texto digitado no campo de busca da tela inicial.
     *
     * @param texto conteudo do EditText
     * @return true quando a busca pode ser realizada
     */
    fun buscaEhValida(texto: String): Boolean = texto.trim().length >= 3
}
