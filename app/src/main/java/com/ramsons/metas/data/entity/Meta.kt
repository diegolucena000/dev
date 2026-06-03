package com.ramsons.metas.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "metas")
data class Meta(
    @PrimaryKey
    val categoria: String,
    val valorMeta: Double
)
