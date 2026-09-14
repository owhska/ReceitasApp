package br.com.receitasapp.model

/**
 * CAMADA MODEL (Requisito 2 - MVVM).
 *
 * Aqui ficam apenas as classes que representam os dados da aplicacao.
 * Nenhuma dessas classes conhece a View ou o Android SDK.
 */

data class Ingrediente(
    val nome: String,
    val quantidade: Double,
    val unidade: String
)

data class Receita(
    val id: Int,
    val nome: String,
    val categoria: String,
    val emoji: String,
    val tempoPreparoMin: Int,
    val porcoesBase: Int,
    val dificuldade: String,
    val ingredientes: List<Ingrediente>,
    val modoPreparo: List<String>
)

data class Categoria(
    val nome: String,
    val emoji: String,
    val quantidadeReceitas: Int
)
