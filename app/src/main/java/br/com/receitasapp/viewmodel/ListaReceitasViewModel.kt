package br.com.receitasapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.receitasapp.data.ReceitaRepository
import br.com.receitasapp.model.Receita

/**
 * CAMADA VIEWMODEL da tela de lista.
 *
 * Recebe o parametro que veio pela Intent (categoria ou termo de busca)
 * e decide qual conjunto de receitas deve ser exibido.
 */
class ListaReceitasViewModel : ViewModel() {

    private val _receitas = MutableLiveData<List<Receita>>()
    val receitas: LiveData<List<Receita>> = _receitas

    private val _titulo = MutableLiveData<String>()
    val titulo: LiveData<String> = _titulo

    /**
     * Usa o valor recebido por Intent para filtrar a lista.
     *
     * @param categoria categoria escolhida no GridView (pode ser nula)
     * @param busca texto digitado na tela inicial (pode ser nulo)
     */
    fun carregar(categoria: String?, busca: String?) {
        when {
            !categoria.isNullOrBlank() -> {
                _titulo.value = "Categoria: $categoria"
                _receitas.value = ReceitaRepository.listarPorCategoria(categoria)
            }
            !busca.isNullOrBlank() -> {
                _titulo.value = "Resultados para \"$busca\""
                _receitas.value = ReceitaRepository.buscarPorTexto(busca)
            }
            else -> {
                _titulo.value = "Todas as receitas"
                _receitas.value = ReceitaRepository.listarTodas()
            }
        }
    }

    fun recarregar() {
        _receitas.value = _receitas.value?.toList()
    }
}
