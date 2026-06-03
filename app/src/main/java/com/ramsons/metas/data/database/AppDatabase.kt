package com.ramsons.metas.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ramsons.metas.data.dao.ConfigDao
import com.ramsons.metas.data.dao.MetaDao
import com.ramsons.metas.data.dao.VendaDao
import com.ramsons.metas.data.entity.Config
import com.ramsons.metas.data.entity.Meta
import com.ramsons.metas.data.entity.Venda

@Database(
    entities = [Venda::class, Meta::class, Config::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vendaDao(): VendaDao
    abstract fun metaDao(): MetaDao
    abstract fun configDao(): ConfigDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ramsons_metas.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
