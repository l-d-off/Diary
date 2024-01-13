package com.darf.diary.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.darf.diary.databinding.FragmentEventDetailBinding

class EventDetailFragment : Fragment() {

    private val args by navArgs<EventDetailFragmentArgs>()

    private var _binding: FragmentEventDetailBinding? = null
    val binding: FragmentEventDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentEventDetailBinding == null")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEventDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvDetailEventName.text = args.eventHour.event?.name
            tvEventDateValue.text = args.eventHour.event?.dateStart
            tvEventTimeStartValue.text = args.eventHour.event?.timeStart
            tvEventTimeEndValue.text = args.eventHour.event?.timeFinish
            tvEventDescriptionValue.text = args.eventHour.event?.description
        }
    }
}