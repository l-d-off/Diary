package com.darf.diary.data.mapper

import com.darf.diary.data.database.EventDbModel
import com.darf.diary.data.network.model.EventDto
import com.darf.diary.domain.model.Event
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
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

    fun mapEntityToDbModel(event: Event): EventDbModel {
        val dateStart = event.dateStart
        val timeStart = event.timeStart
        val dateTime = "$dateStart $timeStart"
        return EventDbModel(
            id = 0,
            dateStart = convertLocalDateTimeToTimestamp(dateTime),
            dateFinish = convertLocalDateTimeToTimestamp(dateTime) + SECONDS_IN_HOUR,
            name = event.name,
            description = event.description
        )
    }

    private fun convertLocalDateTimeToTimestamp(date: String): Long {
        val pattern = "yyyy-MM-dd HH:mm"
        val dateFormatter = DateTimeFormatter.ofPattern(pattern)
        val ldt = LocalDateTime.parse(date, dateFormatter)
        return ldt.toEpochSecond(ZoneOffset.of(OFFSET))
    }

    private fun convertTimestampToDate(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * KOEF)
        val date = Date(stamp.time)
        val pattern = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }

    private fun convertTimestampToTime(timestamp: Long?): String {
        if (timestamp == null) return ""
        val stamp = Timestamp(timestamp * KOEF)
        val time = Time(stamp.time)
        val pattern = "HH:mm"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(time)
    }

    companion object {
        private const val OFFSET = "+03:00"
        private const val KOEF = 1000
        private const val SECONDS_IN_HOUR = 3600
    }
}