package com.darf.diary.data.repository

import android.app.Application
import com.darf.diary.data.database.EventDao
import com.darf.diary.data.mapper.EventMapper
import com.darf.diary.data.network.ApiService
import com.darf.diary.domain.EventRepository
import com.darf.diary.domain.model.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    application: Application,
    private val mapper: EventMapper,
    private val eventDao: EventDao,
    private val apiService: ApiService
) : EventRepository {

    private val event = apiService.getEvent(application)

    override suspend fun getEventsByDateStart(dateStart: Long, dateFinish: Long) =
        eventDao.getEventsByDateStart(dateStart, dateFinish).map {
            mapper.mapDbModelToEntity(it)
        }

    override fun loadData() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            eventDao.insertEvent(mapper.mapDtoToDbModel(event))
        }
    }

    override suspend fun saveNewEvent(event: Event) {
        eventDao.insertEvent(mapper.mapEntityToDbModel(event))
    }
}