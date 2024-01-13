package com.darf.diary.domain.usecase

import com.darf.diary.domain.EventRepository
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke() = repository.loadData()
}