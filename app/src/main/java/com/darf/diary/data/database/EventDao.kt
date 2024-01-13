package com.darf.diary.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDao {

    @Query("SELECT * FROM events WHERE dateStart BETWEEN :dateStart AND :dateFinish")
    suspend fun getEventsByDateStart(dateStart: Long, dateFinish: Long): List<EventDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: EventDbModel)
}