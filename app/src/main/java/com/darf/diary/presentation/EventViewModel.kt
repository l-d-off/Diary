package com.darf.diary.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.darf.diary.domain.model.Event
import com.darf.diary.domain.model.HourEvent
import com.darf.diary.domain.usecase.GetEventsByDateStartUseCase
import com.darf.diary.domain.usecase.LoadDataUseCase
import com.darf.diary.domain.usecase.SaveNewEventUseCase
import java.time.LocalTime
import javax.inject.Inject

class EventViewModel @Inject constructor(
    private val getEventsByDateStartUseCase: GetEventsByDateStartUseCase,
    private val loadDataUseCase: LoadDataUseCase,
    private val saveNewEventUseCase: SaveNewEventUseCase
) : ViewModel() {

    private val _timeTable = MutableLiveData<List<HourEvent>>()
    private val timeTable: LiveData<List<HourEvent>>
        get() = _timeTable

    init {
        loadDataUseCase()
    }

    private suspend fun loadEventsByDateStart(dateStart: Long, dateFinish: Long): List<Event> =
        getEventsByDateStartUseCase(dateStart, dateFinish)

    suspend fun getEventsByDateStart(dateStart: Long, dateFinish: Long): LiveData<List<HourEvent>> {
        val events = loadEventsByDateStart(dateStart, dateFinish)
        val hourEvents = mutableListOf<HourEvent>()
        for (hour in 0 until 24) {
            hourEvents.add(HourEvent(LocalTime.of(hour, 0), null))
            events.forEach {
                val timeStart = LocalTime.parse(it.timeStart)
                if (hour == timeStart.hour) {
                    hourEvents.add(HourEvent(LocalTime.of(hour, timeStart.minute), it))
                }
            }
        }
        _timeTable.postValue(hourEvents)
        return timeTable
    }

    suspend fun saveNewEvent(event: Event) {
        saveNewEventUseCase(event)
    }
}