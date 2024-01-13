package com.darf.diary.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "events")
data class EventDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val dateStart: Long?,
    val dateFinish: Long?,
    val name: String?,
    val description: String?
)