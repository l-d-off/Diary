package com.darf.diary.presentation.recycler

import androidx.recyclerview.widget.DiffUtil
import com.darf.diary.domain.model.HourEvent

object EventDiffCallback: DiffUtil.ItemCallback<HourEvent>() {
    override fun areItemsTheSame(oldItem: HourEvent, newItem: HourEvent): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(oldItem: HourEvent, newItem: HourEvent): Boolean {
        return oldItem == newItem
    }
}