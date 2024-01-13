package com.darf.diary.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val dateStart: String?,
    val timeStart: String?,
    val dateFinish: String?,
    val timeFinish: String?,
    val name: String?,
    val description: String?
) : Parcelable