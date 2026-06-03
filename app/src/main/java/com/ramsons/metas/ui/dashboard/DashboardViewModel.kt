package com.ramsons.metas.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ramsons.metas.data.database.AppDatabase
import com.ramsons.metas.util.Categorias
import com.ramsons.metas.util.FormatUtils
import com.ramsons.metas.util.TipoCat
import kotlinx.coroutines.launch

data class MetaCard(
    val categoria: String,
    val nome: String,
    val emoji: String,
    val atual: Double,
    val meta: Double,
    val tipo: TipoCat,
    val pct: Int,
    val falta: Double,
    val porDia: Double
)

data class ResumoData(
    val totalVendido: Double,
    val totalFalta: Double,
    val porDia: Double,
    val metasBatidas: Int,
    val totalMetas: Int,
    val diasRestantes: Int,
    val mesNome: String
)

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val mes = FormatUtils.mesAtual()

    private val _cards = MutableLiveData<List<MetaCard>>()
    val cards: LiveData<List<MetaCard>> = _cards

    private val _resumo = MutableLiveData<ResumoData>()
    val resumo: LiveData<ResumoData> = _resumo

    init {
        carregarDados()
    }

    fun carregarDados() {
        viewModelScope.launch {
            val diasUteis = db.configDao().get("diasUteis")?.toIntOrNull() ?: 22
            val diasPassados = db.configDao().get("diasPassados")?.toIntOrNull() ?: 0
            val restantes = maxOf(1, diasUteis - diasPassados)

            val metas = db.metaDao().getAllMetasList().associateBy { it.categoria }

            val cards = mutableListOf<MetaCard>()
            var totalVendido = 0.0
            var totalMeta = 0.0
            var totalFalta = 0.0
            var batidas = 0

            for (cat in Categorias.TODAS) {
                val meta = metas[cat.chave]?.valorMeta ?: 0.0
                val atual = db.vendaDao().getTotalPorCategoria(mes, cat.chave) ?: 0.0
                val pct = if (meta > 0) minOf(100, ((atual / meta) * 100).toInt()) else 0
                val falta = maxOf(0.0, meta - atual)
                val porDia = if (restantes > 0) falta / restantes else 0.0

                if (cat.tipo == TipoCat.VALOR) {
                    totalVendido += atual
                    totalMeta += meta
                    totalFalta += falta
                }
                if (atual >= meta && meta > 0) batidas++

                cards.add(MetaCard(
                    categoria = cat.chave,
                    nome = cat.nome,
                    emoji = cat.emoji,
                    atual = atual,
                    meta = meta,
                    tipo = cat.tipo,
                    pct = pct,
                    falta = falta,
                    porDia = porDia
                ))
            }

            _cards.postValue(cards)
            _resumo.postValue(ResumoData(
                totalVendido = totalVendido,
                totalFalta = totalFalta,
                porDia = if (restantes > 0) totalFalta / restantes else 0.0,
                metasBatidas = batidas,
                totalMetas = Categorias.TODAS.size,
                diasRestantes = restantes,
                mesNome = FormatUtils.nomeMesAno(mes)
            ))
        }
    }
}
