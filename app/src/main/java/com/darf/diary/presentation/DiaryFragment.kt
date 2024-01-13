package com.darf.diary.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.darf.diary.databinding.FragmentDiaryBinding
import com.darf.diary.domain.model.HourEvent
import com.darf.diary.presentation.recycler.EventAdapter
import com.darf.diary.utils.CalendarUtils
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

class DiaryFragment : Fragment() {

    private var _binding: FragmentDiaryBinding? = null
    val binding: FragmentDiaryBinding
        get() = _binding ?: throw RuntimeException("FragmentDiaryBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as DiaryApp).component
    }

    private lateinit var viewModel: EventViewModel

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDiaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = EventAdapter()
        binding.eventsRV.adapter = adapter
        viewModel = ViewModelProvider(this, viewModelFactory)[EventViewModel::class.java]
        setDayView(adapter, CalendarUtils.selectedDate.toString())
        with(binding) {
            prevDayBtn.setOnClickListener {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1)
                setDayView(adapter, CalendarUtils.selectedDate.toString())
            }
            nextDayBtn.setOnClickListener {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1)
                setDayView(adapter, CalendarUtils.selectedDate.toString())
            }
        }
        adapter.onEventInfoClickListener = {
            launchEventDetailFragment(it)
        }
    }

    private fun setDayView(adapter: EventAdapter, date: String) {
        val dateStart = CalendarUtils.dateToMillis(date, LocalTime.MIDNIGHT)
        val dateFinish = CalendarUtils.dateToMillis(date, LocalTime.MAX)

        binding.monthDayText.text = CalendarUtils.monthDayFromDate(CalendarUtils.selectedDate)
        val dayOfWeek = CalendarUtils.selectedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        binding.dayOfWeekTV.text = dayOfWeek

        lifecycleScope.launch {
            viewModel.getEventsByDateStart(dateStart, dateFinish)
                .observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
        }
    }

    private fun launchEventDetailFragment(hourEvent: HourEvent) {
        findNavController().navigate(DiaryFragmentDirections.actionDiaryFragmentToEventDetail(hourEvent))
    }
}