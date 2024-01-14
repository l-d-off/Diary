package com.darf.diary.domain.usecase

import com.darf.diary.domain.EventRepository
import com.darf.diary.domain.model.Event
import javax.inject.Inject

class SaveNewEventUseCase @Inject constructor(private val repository: EventRepository) {
    suspend operator fun invoke(event: Event) = repository.saveNewEvent(event)
}