package com.darf.diary.domain

import com.darf.diary.domain.model.Event

interface EventRepository {
    suspend fun getEventsByDateStart (dateStart: Long, dateFinish: Long) : List<Event>

    fun loadData()

    suspend fun saveNewEvent (event: Event)
}