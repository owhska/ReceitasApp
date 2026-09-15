package br.com.receitasapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.receitasapp.data.ReceitaRepository

/**
 * CAMADA VIEWMODEL (Requisito 2 - MVVM) da tela de cadastro.
 *
 * Isola toda a logica de validacao e persistencia de dados da Activity.
 */
class CadastroViewModel : ViewModel() {

    private val _cadastroSucesso = MutableLiveData<Boolean>()
    val cadastroSucesso: LiveData<Boolean> = _cadastroSucesso

    private val _erroValidacao = MutableLiveData<Boolean>()
    val erroValidacao: LiveData<Boolean> = _erroValidacao

    fun cadastrarReceita(
        nome: String,
        categoria: String,
        tempoTexto: String,
        porcoesTexto: String,
        dificuldade: String,
        ingredientes: String,
        preparo: String
    ) {
        if (nome.isBlank() || categoria.isBlank() || tempoTexto.isBlank() ||
            porcoesTexto.isBlank() || dificuldade.isBlank() ||
            ingredientes.isBlank() || preparo.isBlank()
        ) {
            _erroValidacao.value = true
            return
        }

        val tempo = tempoTexto.toIntOrNull() ?: 0
        val porcoes = porcoesTexto.toIntOrNull() ?: 0

        ReceitaRepository.adicionarReceita(
            nome = nome,
            categoria = categoria,
            tempo = tempo,
            porcoes = porcoes,
            dificuldade = dificuldade,
            ingredientesTexto = ingredientes,
            preparoTexto = preparo
        )

        _cadastroSucesso.value = true
    }
}
