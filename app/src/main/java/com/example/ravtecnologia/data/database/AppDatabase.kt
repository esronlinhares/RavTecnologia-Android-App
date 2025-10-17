package com.example.ravtecnologia.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.ravtecnologia.data.dao.ActivityDao
import com.example.ravtecnologia.data.entity.ActivityEntity

@Database(
    entities = [ActivityEntity::class], // Lista de entidades (tabelas) do banco
    version = 1,                        // Versão do banco, importante para migrações
    exportSchema = false                // Não exporta schema para pasta
)
@TypeConverters(Converters::class) // Permite converter tipos que o Room não suporta nativamente
abstract class AppDatabase : RoomDatabase() {

    // Retorna o DAO para acessar atividades
    abstract fun activityDao(): ActivityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        // @Volatile garante que todas as threads vejam a mesma instância atualizada

        fun getDatabase(context: Context): AppDatabase {
            // Retorna a instância existente ou cria uma nova se for null
            return INSTANCE ?: synchronized(this) {
                // synchronized garante que apenas uma thread crie o banco ao mesmo tempo
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "activities_database" // Nome do arquivo do banco
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
