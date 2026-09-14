package br.com.receitasapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.receitasapp.data.ReceitaRepository
import br.com.receitasapp.model.Categoria
import br.com.receitasapp.util.ReceitaUtils

/**
 * CAMADA VIEWMODEL (Requisito 2 - MVVM) da tela inicial.
 *
 * A Activity apenas observa os LiveData expostos aqui; toda a regra
 * (montar categorias, contar receitas, validar a busca) fica nesta classe.
 */
class MainViewModel : ViewModel() {

    private val _categorias = MutableLiveData<List<Categoria>>()
    val categorias: LiveData<List<Categoria>> = _categorias

    private val _totalReceitas = MutableLiveData<Int>()
    val totalReceitas: LiveData<Int> = _totalReceitas

    private val _totalFavoritas = MutableLiveData<Int>()
    val totalFavoritas: LiveData<Int> = _totalFavoritas

    init {
        carregar()
    }

    fun carregar() {
        _categorias.value = ReceitaRepository.listarCategorias()
        _totalReceitas.value = ReceitaRepository.listarTodas().size
        atualizarFavoritas()
    }

    fun atualizarFavoritas() {
        _totalFavoritas.value = ReceitaRepository.totalFavoritas()
    }

    /** Requisito 1: recebe parametro e retorna um valor. */
    fun validarBusca(texto: String): Boolean = ReceitaUtils.buscaEhValida(texto)
}
