package br.com.receitasapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.receitasapp.adapter.CategoriaAdapter
import br.com.receitasapp.databinding.ActivityMainBinding
import br.com.receitasapp.viewmodel.MainViewModel

/**
 * TELA 1 - HOME (layout XML).
 *
 * Requisito 4: esta Activity usa VIEW BINDING (ActivityMainBinding).
 * Requisito 5: TextView, EditText, Button e GridView.
 * Requisito 6: ConstraintLayout.
 * Requisito 3: envia parametros para a proxima tela por Intent.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoriaAdapter: CategoriaAdapter

    // Requisito 2 (MVVM): a View nao guarda regra de negocio, apenas observa a ViewModel
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // VIEW BINDING: nenhum findViewById nesta tela
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarGrid()
        configurarBusca()
        observarViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.atualizarFavoritas()
    }

    private fun configurarGrid() {
        categoriaAdapter = CategoriaAdapter(this, emptyList())
        binding.gridCategorias.adapter = categoriaAdapter

        binding.gridCategorias.setOnItemClickListener { _, _, position, _ ->
            val categoria = categoriaAdapter.getItem(position)
            abrirLista(categoria = categoria.nome, busca = null)
        }
    }

    private fun configurarBusca() {
        binding.btnBuscar.setOnClickListener {
            val texto = binding.edtBusca.text?.toString().orEmpty()

            // A validacao fica na ViewModel (MVVM)
            if (!viewModel.validarBusca(texto)) {
                binding.tilBusca.error = getString(R.string.erro_busca)
            } else {
                binding.tilBusca.error = null
                abrirLista(categoria = null, busca = texto.trim())
            }
        }
    }

    private fun observarViewModel() {
        viewModel.categorias.observe(this) { lista ->
            categoriaAdapter.atualizar(lista)
        }
        viewModel.totalReceitas.observe(this) { total ->
            binding.txtResumo.text = getString(R.string.resumo_home, total)
        }
        viewModel.totalFavoritas.observe(this) { total ->
            binding.txtFavoritos.text = getString(R.string.favoritos_home, total)
        }
    }

    /**
     * REQUISITO 3 - Navegacao com passagem de parametros por Intent.
     * O valor enviado aqui e lido e utilizado pela ListaReceitasActivity.
     */
    private fun abrirLista(categoria: String?, busca: String?) {
        val intent = Intent(this, ListaReceitasActivity::class.java)
        intent.putExtra(ListaReceitasActivity.EXTRA_CATEGORIA, categoria)
        intent.putExtra(ListaReceitasActivity.EXTRA_BUSCA, busca)
        startActivity(intent)
    }
}
