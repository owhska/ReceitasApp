package br.com.receitasapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.receitasapp.databinding.ItemCategoriaBinding
import br.com.receitasapp.model.Categoria

/**
 * Adapter do GridView da tela inicial (Requisito 5).
 *
 * REQUISITO 4: este adapter monta as views usando VIEW BINDING
 * (classe ItemCategoriaBinding, gerada a partir de item_categoria.xml).
 */
class CategoriaAdapter(
    private val context: Context,
    private var categorias: List<Categoria>
) : BaseAdapter() {

    override fun getCount(): Int = categorias.size

    override fun getItem(position: Int): Categoria = categorias[position]

    override fun getItemId(position: Int): Long = position.toLong()

    fun atualizar(novaLista: List<Categoria>) {
        categorias = novaLista
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemCategoriaBinding
        val view: View

        if (convertView == null) {
            binding = ItemCategoriaBinding.inflate(LayoutInflater.from(context), parent, false)
            view = binding.root
            view.tag = binding
        } else {
            view = convertView
            binding = view.tag as ItemCategoriaBinding
        }

        val categoria = getItem(position)
        binding.txtEmojiCategoria.text = categoria.emoji
        binding.txtNomeCategoria.text = categoria.nome
        binding.txtQtdCategoria.text = "${categoria.quantidadeReceitas} receitas"

        return view
    }
}
