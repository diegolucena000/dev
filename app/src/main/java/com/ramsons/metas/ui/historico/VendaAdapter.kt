package com.ramsons.metas.ui.historico

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramsons.metas.data.entity.Venda
import com.ramsons.metas.databinding.ItemVendaBinding
import com.ramsons.metas.util.Categorias
import com.ramsons.metas.util.FormatUtils
import com.ramsons.metas.util.TipoCat

class VendaAdapter(private val onDelete: (Venda) -> Unit) :
    ListAdapter<Venda, VendaAdapter.ViewHolder>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Venda>() {
            override fun areItemsTheSame(a: Venda, b: Venda) = a.id == b.id
            override fun areContentsTheSame(a: Venda, b: Venda) = a == b
        }
    }

    inner class ViewHolder(private val binding: ItemVendaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(venda: Venda) {
            val cat = Categorias.porChave(venda.categoria)
            binding.tvCat.text = "${cat?.emoji ?: ""} ${cat?.nome ?: venda.categoria}"
            binding.tvCliente.text = if (venda.cliente.isBlank()) "Cliente não informado" else venda.cliente
            binding.tvData.text = FormatUtils.formatDataBR(venda.data)

            val tipo = cat?.tipo ?: TipoCat.VALOR
            binding.tvValor.text = FormatUtils.formatValor(venda.valor, tipo)

            binding.btnDeletar.setOnClickListener { onDelete(venda) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemVendaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
