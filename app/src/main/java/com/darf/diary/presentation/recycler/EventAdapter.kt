package com.darf.diary.presentation.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.darf.diary.R
import com.darf.diary.databinding.ItemEventInfoBinding
import com.darf.diary.databinding.ItemEventInfoDividerBinding
import com.darf.diary.domain.model.HourEvent
import com.darf.diary.utils.CalendarUtils
import java.time.LocalTime

class EventAdapter : ListAdapter<HourEvent, EventViewHolder>(EventDiffCallback) {

    var onEventInfoClickListener: ((HourEvent) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding = ItemEventInfoBinding.inflate(inflater, parent, false)
                EventViewHolder(binding)
            }

            VIEW_TYPE_ITEM_DIVIDER -> {
                val binding = ItemEventInfoDividerBinding.inflate(inflater, parent, false)
                EventViewHolder(binding)
            }

            else -> throw Exception("Неверный тип viewType")
        }
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val hourEvent = getItem(position)
        val eventHour = hourEvent.time.hour
        val eventMinute = hourEvent.time.minute
        if (getItem(position).event == null) {
            holder.itemEventInfoDividerBinding?.run {
                tvTime.text = CalendarUtils.formattedShortTime(LocalTime.of(eventHour, 0))
            }
        } else {
            holder.itemEventInfoBinding?.run {
                tvTime.text = CalendarUtils.formattedShortTime(LocalTime.of(eventHour, eventMinute))
                tvEventName.text = hourEvent.event?.name
                tvEventDescription.text = hourEvent.event?.description
                root.setOnClickListener {
                    onEventInfoClickListener?.invoke(hourEvent)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).event != null) {
            VIEW_TYPE_ITEM
        } else {
            VIEW_TYPE_ITEM_DIVIDER
        }
    }

    companion object {
        private val VIEW_TYPE_ITEM = R.layout.item_event_info
        private val VIEW_TYPE_ITEM_DIVIDER = R.layout.item_event_info_divider
    }
}