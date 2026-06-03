package com.ramsons.metas.ui.lancar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ramsons.metas.data.database.AppDatabase
import com.ramsons.metas.data.entity.Venda
import com.ramsons.metas.util.FormatUtils
import kotlinx.coroutines.launch

class LancarViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)

    val sucesso = MutableLiveData<Boolean>()
    val erro = MutableLiveData<String>()

    fun registrarVenda(categoria: String, cliente: String, valorStr: String, data: String) {
        val valor = valorStr.replace(",", ".").toDoubleOrNull()
        if (valor == null || valor <= 0) {
            erro.value = "Informe um valor válido!"
            return
        }
        if (data.isBlank()) {
            erro.value = "Informe a data!"
            return
        }

        viewModelScope.launch {
            val mes = data.substring(0, 7) // YYYY-MM
            val venda = Venda(
                categoria = categoria,
                cliente = cliente.trim(),
                valor = valor,
                data = data,
                mes = mes
            )
            db.vendaDao().inserir(venda)
            sucesso.postValue(true)
        }
    }
}
