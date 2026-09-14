package br.com.receitasapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.com.receitasapp.R
import br.com.receitasapp.data.ReceitaRepository
import br.com.receitasapp.model.Receita
import br.com.receitasapp.util.ReceitaUtils

/**
 * Adapter do ListView da tela de lista (Requisito 5).
 *
 * REQUISITO 4: aqui as views sao obtidas com FIND VIEW BY ID,
 * diferente do CategoriaAdapter, que usa View Binding.
 */
class ReceitaAdapter(
    private val context: Context,
    private var receitas: List<Receita>
) : BaseAdapter() {

    override fun getCount(): Int = receitas.size

    override fun getItem(position: Int): Receita = receitas[position]

    override fun getItemId(position: Int): Long = receitas[position].id.toLong()

    fun atualizar(novaLista: List<Receita>) {
        receitas = novaLista
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView
            ?: LayoutInflater.from(context).inflate(R.layout.item_receita, parent, false)

        val txtEmoji = view.findViewById<TextView>(R.id.txtEmojiReceita)
        val txtNome = view.findViewById<TextView>(R.id.txtNomeReceita)
        val txtInfo = view.findViewById<TextView>(R.id.txtInfoReceita)
        val txtFavorito = view.findViewById<TextView>(R.id.txtFavoritoReceita)

        val receita = getItem(position)
        txtEmoji.text = receita.emoji
        txtNome.text = receita.nome
        // Requisito 1: funcao que recebe parametro e retorna um valor
        txtInfo.text = ReceitaUtils.resumoDaReceita(receita)
        txtFavorito.visibility =
            if (ReceitaRepository.ehFavorita(receita.id)) View.VISIBLE else View.GONE

        return view
    }
}
