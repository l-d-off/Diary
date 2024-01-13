package com.darf.diary.domain.usecase

import com.darf.diary.domain.EventRepository
import javax.inject.Inject

class GetEventsByDateStartUseCase @Inject constructor(
    private val repository: EventRepository
) {
    suspend operator fun invoke(dateStart: Long, dateFinish: Long) =
        repository.getEventsByDateStart(dateStart, dateFinish)
}