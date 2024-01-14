package com.darf.diary.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.darf.diary.databinding.FragmentEditEventBinding
import com.darf.diary.domain.model.Event
import com.darf.diary.utils.CalendarUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

class EditEventFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: EventViewModel

    private val component by lazy {
        (requireActivity().application as DiaryApp).component
    }

    private var _binding: FragmentEditEventBinding? = null
    val binding: FragmentEditEventBinding
        get() = _binding ?: throw RuntimeException("FragmentEventDetailBinding == null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[EventViewModel::class.java]
        val dateStart = CalendarUtils.selectedDate.toString()
        val timeStart = LocalTime.of(LocalTime.now().hour, 0).toString()
        with(binding) {
            eventDateTV.text = dateStart
            eventTimeTV.text = timeStart
            saveNewEventBTN.setOnClickListener {
                val name = binding.eventNameET.text.toString()
                val description = binding.eventDescriptionET.text.toString()
                val event = Event(dateStart, timeStart, dateStart, timeStart, name, description)
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.saveNewEvent(event)
                }
                findNavController().navigate(EditEventFragmentDirections.actionEditEventFragmentToScheduleFragment())
            }
        }
    }
}