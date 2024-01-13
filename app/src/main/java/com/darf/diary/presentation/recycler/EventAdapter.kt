package com.darf.diary.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.darf.diary.databinding.ItemEventInfoBinding
import com.darf.diary.utils.CalendarUtils
import com.darf.diary.domain.model.HourEvent
import java.time.LocalTime

class EventAdapter : ListAdapter<HourEvent, EventViewHolder>(EventDiffCallback) {

    var onEventInfoClickListener: ((HourEvent) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val hourEvent = getItem(position)
        val eventHour = hourEvent.time.hour
        with(holder.binding) {
            tvTime.text = CalendarUtils.formattedShortTime(LocalTime.of(eventHour, 0))
            tvEventName.text = hourEvent.event?.name
            tvEventDescription.text = hourEvent.event?.description
            root.setOnClickListener {
                onEventInfoClickListener?.invoke(hourEvent)
            }
        }
    }
}