package com.ramsons.metas.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vendas")
data class Venda(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoria: String,       // produtos, garantia, seguro, psd, crediario, ar
    val cliente: String,
    val valor: Double,
    val data: String,            // YYYY-MM-DD
    val mes: String              // YYYY-MM (para filtrar por mês)
)
