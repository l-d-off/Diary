package com.darf.diary.presentation.recycler

import androidx.recyclerview.widget.RecyclerView
import com.darf.diary.databinding.ItemEventInfoBinding
import com.darf.diary.databinding.ItemEventInfoDividerBinding

class EventViewHolder : RecyclerView.ViewHolder {
    var itemEventInfoBinding: ItemEventInfoBinding? = null
    var itemEventInfoDividerBinding: ItemEventInfoDividerBinding? = null

    constructor(binding: ItemEventInfoBinding) : super(binding.root) {
        itemEventInfoBinding = binding
    }

    constructor(binding: ItemEventInfoDividerBinding) : super(binding.root) {
        itemEventInfoDividerBinding = binding
    }
}