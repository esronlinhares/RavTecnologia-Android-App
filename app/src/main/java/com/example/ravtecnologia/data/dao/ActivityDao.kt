package com.example.ravtecnologia.data.dao

import androidx.room.*
import com.example.ravtecnologia.data.entity.ActivityEntity

@Dao
interface ActivityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: ActivityEntity)

    @Update
    suspend fun updateActivity(activity: ActivityEntity)

    @Delete
    suspend fun deleteActivity(activity: ActivityEntity)

    @Query("SELECT * FROM activities ORDER BY dataLimite ASC")
    suspend fun getAllActivities(): List<ActivityEntity>

    @Query("SELECT * FROM activities WHERE id = :id")
    suspend fun getActivityById(id: Int): ActivityEntity?

}