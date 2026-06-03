package com.ramsons.metas.ui.config

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ramsons.metas.data.database.AppDatabase
import com.ramsons.metas.data.entity.Config
import com.ramsons.metas.data.entity.Meta
import com.ramsons.metas.util.Categorias
import kotlinx.coroutines.launch

data class ConfigState(
    val metas: Map<String, Double>,
    val diasUteis: Int,
    val diasPassados: Int
)

class ConfigViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)

    val state = MutableLiveData<ConfigState>()
    val salvoOk = MutableLiveData<String>()

    init {
        carregar()
    }

    fun carregar() {
        viewModelScope.launch {
            val metas = db.metaDao().getAllMetasList().associateBy({ it.categoria }, { it.valorMeta })
            val diasUteis = db.configDao().get("diasUteis")?.toIntOrNull() ?: 22
            val diasPassados = db.configDao().get("diasPassados")?.toIntOrNull() ?: 0
            state.postValue(ConfigState(metas, diasUteis, diasPassados))
        }
    }

    fun salvarMetas(valores: Map<String, String>) {
        viewModelScope.launch {
            val lista = valores.map { (k, v) ->
                Meta(categoria = k, valorMeta = v.replace(",", ".").toDoubleOrNull() ?: 0.0)
            }
            db.metaDao().inserirOuAtualizarTodas(lista)
            salvoOk.postValue("Metas salvas com sucesso!")
        }
    }

    fun salvarDias(diasUteis: String, diasPassados: String) {
        viewModelScope.launch {
            db.configDao().set(Config("diasUteis", diasUteis.ifBlank { "22" }))
            db.configDao().set(Config("diasPassados", diasPassados.ifBlank { "0" }))
            salvoOk.postValue("Dias atualizados!")
            carregar()
        }
    }
}
