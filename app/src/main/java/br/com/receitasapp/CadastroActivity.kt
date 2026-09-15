package br.com.receitasapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.receitasapp.databinding.ActivityCadastroBinding
import br.com.receitasapp.viewmodel.CadastroViewModel

/**
 * TELA DE CADASTRO DE RECEITAS (layout XML).
 *
 * Requisito 4: esta Activity usa VIEW BINDING (ActivityCadastroBinding).
 * Requisito 2 (MVVM): a View nao guarda regra de negocio ou validacao, apenas observa a ViewModel.
 */
class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    
    // Requisito 2 (MVVM): Instanciando a ViewModel da tela de cadastro
    private val viewModel: CadastroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarCadastro.setNavigationOnClickListener {
            finish()
        }

        binding.btnSalvar.setOnClickListener {
            // Apenas extrai os textos da tela e repassa para a ViewModel processar
            viewModel.cadastrarReceita(
                nome = binding.edtNome.text?.toString().orEmpty(),
                categoria = binding.edtCategoria.text?.toString().orEmpty(),
                tempoTexto = binding.edtTempo.text?.toString().orEmpty(),
                porcoesTexto = binding.edtPorcoes.text?.toString().orEmpty(),
                dificuldade = binding.edtDificuldade.text?.toString().orEmpty(),
                ingredientes = binding.edtIngredientes.text?.toString().orEmpty(),
                preparo = binding.edtPreparo.text?.toString().orEmpty()
            )
        }

        observarViewModel()
    }

    private fun observarViewModel() {
        // Observa se o cadastro deu certo para exibir mensagem e fechar a tela
        viewModel.cadastroSucesso.observe(this) { sucesso ->
            if (sucesso) {
                Toast.makeText(this, R.string.sucesso_cadastro, Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        // Observa se houve erro de validacao para avisar o usuario
        viewModel.erroValidacao.observe(this) { erro ->
            if (erro) {
                Toast.makeText(this, R.string.erro_cadastro, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
