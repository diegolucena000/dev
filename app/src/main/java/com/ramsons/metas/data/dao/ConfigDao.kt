package com.ramsons.metas.data.dao

import androidx.room.*
import com.ramsons.metas.data.entity.Config

@Dao
interface ConfigDao {

    @Query("SELECT valor FROM config WHERE chave = :chave")
    suspend fun get(chave: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun set(config: Config)
}
