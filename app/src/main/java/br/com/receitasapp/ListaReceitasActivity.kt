package br.com.receitasapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.receitasapp.adapter.ReceitaAdapter
import br.com.receitasapp.viewmodel.ListaReceitasViewModel
import com.google.android.material.appbar.MaterialToolbar

/**
 * TELA 2 - LISTA DE RECEITAS (layout XML).
 */
class ListaReceitasActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CATEGORIA = "extra_categoria"
        const val EXTRA_BUSCA = "extra_busca"
    }

    private lateinit var toolbar: MaterialToolbar
    private lateinit var txtTitulo: TextView
    private lateinit var txtQtdResultados: TextView
    private lateinit var txtListaVazia: TextView
    private lateinit var listReceitas: ListView
    private lateinit var receitaAdapter: ReceitaAdapter

    private val viewModel: ListaReceitasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_receitas)

        // FIND VIEW BY ID (Requisito 4)
        toolbar = findViewById(R.id.toolbarLista)
        txtTitulo = findViewById(R.id.txtTituloLista)
        txtQtdResultados = findViewById(R.id.txtQtdResultados)
        txtListaVazia = findViewById(R.id.txtListaVazia)
        listReceitas = findViewById(R.id.listReceitas)

        toolbar.title = getString(R.string.app_name)
        toolbar.setNavigationOnClickListener { finish() }

        receitaAdapter = ReceitaAdapter(this, emptyList())
        listReceitas.adapter = receitaAdapter

        // REQUISITO 3 - leitura dos parametros recebidos por Intent
        val categoria = intent.getStringExtra(EXTRA_CATEGORIA)
        val busca = intent.getStringExtra(EXTRA_BUSCA)

        // O valor recebido e usado pela ViewModel para montar a lista
        viewModel.carregar(categoria, busca)

        observarViewModel()
        configurarCliqueDaLista()
    }

    override fun onResume() {
        super.onResume()
        // atualiza a estrela de favorito ao voltar da tela de detalhe
        receitaAdapter.notifyDataSetChanged()
    }

    private fun observarViewModel() {
        viewModel.titulo.observe(this) { titulo ->
            txtTitulo.text = titulo
        }
        viewModel.receitas.observe(this) { lista ->
            receitaAdapter.atualizar(lista)
            txtQtdResultados.text = getString(R.string.qtd_encontradas, lista.size)
            txtListaVazia.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun configurarCliqueDaLista() {
        listReceitas.setOnItemClickListener { _, _, position, _ ->
            val receita = receitaAdapter.getItem(position)

            // REQUISITO 3 - passagem de parametro por Intent para a tela Compose
            val intent = Intent(this, DetalheReceitaActivity::class.java)
            intent.putExtra(DetalheReceitaActivity.EXTRA_RECEITA_ID, receita.id)
            intent.putExtra(DetalheReceitaActivity.EXTRA_RECEITA_NOME, receita.nome)
            startActivity(intent)
        }
    }
}
