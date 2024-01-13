package com.darf.diary.data.mapper

import com.darf.diary.data.database.EventDbModel
import com.darf.diary.data.network.model.EventDto
import com.darf.diary.domain.model.Event
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class EventMapper @Inject constructor() {

    fun mapDtoToDbModel(dto: EventDto) = EventDbModel(
        id = dto.id,
        dateStart = dto.dateStart,
        dateFinish = dto.dateFinish,
        name = dto.name,
        description = dto.description
    )

    fun mapDbModelToEntity(db: EventDbModel) = Event(
        name = db.name,
        dateStart = convertTimestampToDate(db.dateStart),
        timeStart = convertTimestampToTime(db.dateStart),
        dateFinish = convertTimestampToDate(db.dateFinish),
        timeFinish = convertTimestampToTime(db.dateFinish),
        description = db.description
    )

    private fun convertTimestampToDate(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val date = Date(stamp.time)
        val pattern = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * 1000)
        val time = Time(stamp.time)
        val pattern = "HH:mm"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(time)
    }
}