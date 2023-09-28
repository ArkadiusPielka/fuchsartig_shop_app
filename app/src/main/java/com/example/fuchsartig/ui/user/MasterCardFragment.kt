package com.example.fuchsartig.ui.user

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fuchsartig.R
import com.example.fuchsartig.databinding.FragmentMasterCardBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class MasterCardFragment : Fragment() {

private lateinit var binding: FragmentMasterCardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMasterCardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardValid()

        binding.btnSaveCard.setOnClickListener {

        }
    }
    private fun cardValid() {
        val inputCardDate = binding.inputCardDate

        // Setze den Klicklistener für das Eingabefeld
        inputCardDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, _ ->
                    // Hier erhalten Sie das ausgewählte Datum (Tag wird nicht verwendet)
                    val formattedDate = String.format(Locale.getDefault(), "%02d.%04d", selectedMonth + 1, selectedYear)
                    inputCardDate.setText(formattedDate)
                },
                year,
                month,
                day
            )

            // Zeige den DatePickerDialog
            datePickerDialog.show()
        }
    }


//    fun cardValid(): String {
//
//        val inputCardValid = binding.inputCardDate
//
//        val constraintsBuilder = CalendarConstraints.Builder()
//        val maxCalendar = MaterialDatePicker.todayInUtcMilliseconds()
//        constraintsBuilder.setEnd(maxCalendar)
//        val constraints = constraintsBuilder.build()
//
//        val builder = MaterialDatePicker.Builder.datePicker()
//            .setTitleText("Wählen Sie Ihr Geburtsdatum")
//            .setCalendarConstraints(constraints)
//
//        val datePicker = builder.build()
//
//        var formattedDate = ""
//
//        inputCardValid.setOnClickListener {
//            datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
//        }
//
//        datePicker.addOnPositiveButtonClickListener { selection ->
//            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
//            calendar.timeInMillis = selection
//
//            val dateFormat = SimpleDateFormat("MM.yyyy", Locale.getDefault())
//            formattedDate = dateFormat.format(calendar.time)
//
//            inputCardValid.setText(formattedDate)
//        }
//
//        return formattedDate
//
//    }
}