package com.ramsons.metas.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ramsons.metas.R
import com.ramsons.metas.databinding.ItemMetaCardBinding
import com.ramsons.metas.util.FormatUtils
import com.ramsons.metas.util.TipoCat

class MetaCardAdapter : ListAdapter<MetaCard, MetaCardAdapter.ViewHolder>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<MetaCard>() {
            override fun areItemsTheSame(a: MetaCard, b: MetaCard) = a.categoria == b.categoria
            override fun areContentsTheSame(a: MetaCard, b: MetaCard) = a == b
        }
    }

    inner class ViewHolder(private val binding: ItemMetaCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: MetaCard) {
            binding.tvEmoji.text = card.emoji
            binding.tvNome.text = card.nome
            binding.tvAtual.text = FormatUtils.formatValor(card.atual, card.tipo)
            binding.tvTotal.text = "de ${FormatUtils.formatValor(card.meta, card.tipo)}"
            binding.progressBar.progress = card.pct

            val faltaStr = FormatUtils.formatValor(card.falta, card.tipo)
            val porDiaStr = FormatUtils.formatValor(card.porDia, card.tipo)
            binding.tvFalta.text = faltaStr
            binding.tvPorDia.text = porDiaStr

            val ctx = binding.root.context

            when {
                card.pct >= 100 -> {
                    binding.tvBadge.text = "✓ Batida"
                    binding.tvBadge.setTextColor(ContextCompat.getColor(ctx, R.color.green))
                    binding.tvBadge.setBackgroundResource(R.drawable.badge_green)
                    binding.progressBar.setIndicatorColor(ContextCompat.getColor(ctx, R.color.green))
                    binding.tvFalta.setTextColor(ContextCompat.getColor(ctx, R.color.green))
                    binding.tvPorDia.setTextColor(ContextCompat.getColor(ctx, R.color.green))
                }
                card.pct >= 60 -> {
                    binding.tvBadge.text = "No caminho"
                    binding.tvBadge.setTextColor(ContextCompat.getColor(ctx, R.color.yellow))
                    binding.tvBadge.setBackgroundResource(R.drawable.badge_yellow)
                    binding.progressBar.setIndicatorColor(ContextCompat.getColor(ctx, R.color.yellow))
                    binding.tvFalta.setTextColor(ContextCompat.getColor(ctx, R.color.yellow))
                    binding.tvPorDia.setTextColor(ContextCompat.getColor(ctx, R.color.yellow))
                }
                else -> {
                    binding.tvBadge.text = "Atenção"
                    binding.tvBadge.setTextColor(ContextCompat.getColor(ctx, R.color.red))
                    binding.tvBadge.setBackgroundResource(R.drawable.badge_red)
                    binding.progressBar.setIndicatorColor(ContextCompat.getColor(ctx, R.color.red))
                    binding.tvFalta.setTextColor(ContextCompat.getColor(ctx, R.color.red))
                    binding.tvPorDia.setTextColor(ContextCompat.getColor(ctx, R.color.red))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMetaCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
