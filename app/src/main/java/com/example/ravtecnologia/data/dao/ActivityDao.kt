package com.example.ravtecnologia.data.dao

import androidx.room.* // Importa as anotações e classes do Room
import com.example.ravtecnologia.data.entity.ActivityEntity
import kotlinx.coroutines.flow.Flow // Flow permite receber listas atualizadas de forma reativa

@Dao // Indica que essa interface é um Data Access Object (DAO) do Room
interface ActivityDao {

    // Inserir uma atividade no banco
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: ActivityEntity)
    /*
    - @Insert: comando para inserir no banco
    - onConflict = REPLACE: se já existir uma atividade com o mesmo id, substitui
    - suspend: função que roda em corrotina, para não travar a UI
    */

    @Update // Atualizar uma atividade existente
    suspend fun updateActivity(activity: ActivityEntity)
    // @Update: atualiza os campos de uma ActivityEntity existente no banco


    @Delete // Deletar uma atividade
    suspend fun deleteActivity(activity: ActivityEntity)
    // @Delete: remove a atividade passada como parâmetro do banco


    // Pegar todas as atividades como Flow, ordenadas pela data limite
    @Query("SELECT * FROM activities ORDER BY dataLimite ASC")
    fun getAllActivitiesFlow(): Flow<List<ActivityEntity>>
    /*
    - @Query: define uma query SQL
    - SELECT *: pega todas as colunas da tabela 'activities'
    - ORDER BY dataLimite ASC: organiza por data limite em ordem crescente
    - Flow<List<ActivityEntity>>: retorna uma lista observável reativamente
    */

    // Pegar uma atividade pelo ID
    @Query("SELECT * FROM activities WHERE id = :id")
    suspend fun getActivityById(id: Int): ActivityEntity?
    /*
    - ":id" indica que o parâmetro da função será usado na query
    - Retorna uma ActivityEntity ou null caso não exista
    */
}
