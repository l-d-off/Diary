package com.darf.diary.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class HourEvent(
    val time: LocalTime,
    val event: Event?
) : Parcelable