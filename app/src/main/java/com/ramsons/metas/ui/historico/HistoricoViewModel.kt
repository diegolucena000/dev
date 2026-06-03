package com.ramsons.metas.ui.historico

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ramsons.metas.data.database.AppDatabase
import com.ramsons.metas.data.entity.Venda
import com.ramsons.metas.util.FormatUtils
import kotlinx.coroutines.launch

class HistoricoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    val mes = FormatUtils.mesAtual()

    val vendas: LiveData<List<Venda>> = db.vendaDao().getVendasDoMes(mes)

    fun deletarVenda(venda: Venda) {
        viewModelScope.launch {
            db.vendaDao().deletar(venda)
        }
    }

    fun limparMes() {
        viewModelScope.launch {
            db.vendaDao().limparMes(mes)
        }
    }
}
