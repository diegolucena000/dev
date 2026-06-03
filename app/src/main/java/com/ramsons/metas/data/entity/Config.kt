package com.ramsons.metas.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "config")
data class Config(
    @PrimaryKey
    val chave: String,
    val valor: String
)
