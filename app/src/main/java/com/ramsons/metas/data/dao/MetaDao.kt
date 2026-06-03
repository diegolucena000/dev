package com.ramsons.metas.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ramsons.metas.data.entity.Meta

@Dao
interface MetaDao {

    @Query("SELECT * FROM metas")
    fun getAllMetas(): LiveData<List<Meta>>

    @Query("SELECT * FROM metas")
    suspend fun getAllMetasList(): List<Meta>

    @Query("SELECT valorMeta FROM metas WHERE categoria = :categoria")
    suspend fun getMetaPorCategoria(categoria: String): Double?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirOuAtualizar(meta: Meta)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirOuAtualizarTodas(metas: List<Meta>)
}
