package com.ramsons.metas.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ramsons.metas.data.entity.Venda

@Dao
interface VendaDao {

    @Query("SELECT * FROM vendas WHERE mes = :mes ORDER BY id DESC")
    fun getVendasDoMes(mes: String): LiveData<List<Venda>>

    @Query("SELECT * FROM vendas WHERE mes = :mes ORDER BY id DESC")
    suspend fun getVendasDoMesList(mes: String): List<Venda>

    @Query("SELECT SUM(valor) FROM vendas WHERE mes = :mes AND categoria = :categoria")
    suspend fun getTotalPorCategoria(mes: String, categoria: String): Double?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(venda: Venda)

    @Delete
    suspend fun deletar(venda: Venda)

    @Query("DELETE FROM vendas WHERE mes = :mes")
    suspend fun limparMes(mes: String)
}
