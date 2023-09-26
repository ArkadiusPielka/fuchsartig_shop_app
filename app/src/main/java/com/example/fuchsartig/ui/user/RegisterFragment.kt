package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.fuchsartig.R
import com.example.fuchsartig.databinding.FragmentRegisterBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.CalendarConstraints
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showFragment()
        birthday()

        binding.btnHome.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }




        binding.btnSavePersonalData.setOnClickListener {

            val firstName = binding.inputFirstName.text.toString().trim()
            val lastName = binding.inputLastName.text.toString().trim()

            if (firstName != "" && lastName != "") {
                binding.inputFirstName.setText("")
                binding.inputLastName.setText("")
                binding.btnDone.visibility = View.VISIBLE
            } else {
                binding.btnDone.visibility = View.INVISIBLE
            }
            binding.btnDropDown.setImageResource(R.drawable.ic_drop_down)
            binding.cvPersonalData.visibility = View.GONE
        }


        binding.btnDropDown.setOnClickListener {
            dropDownPersonalData()
        }

        binding.btnDropDownPayment.setOnClickListener {
            dropDownPayment()
        }
    }

    private fun dropDownPersonalData(){
        if (binding.cvPersonalData.visibility == View.GONE) {
            binding.btnDropDown.setImageResource(R.drawable.ic_drop_up)
            binding.cvPersonalData.visibility = View.VISIBLE
        } else {
            binding.btnDropDown.setImageResource(R.drawable.ic_drop_down)
            binding.cvPersonalData.visibility = View.GONE
        }
    }

    private fun dropDownPayment(){
        if (binding.cvPayment.visibility == View.GONE) {
            binding.btnDropDownPayment.setImageResource(R.drawable.ic_drop_up)
            binding.cvPayment.visibility = View.VISIBLE

        } else {
            binding.btnDropDownPayment.setImageResource(R.drawable.ic_drop_down)
            binding.cvPayment.visibility = View.GONE
            binding.cvFragmentPayment.visibility = View.GONE
            binding.rbGroup.clearCheck()
        }
    }
    private fun showFragment() {
        val rbMasterCard = binding.rbMastercard
        val rbBanking = binding.rbBanking
        val rbPayPal = binding.rbPaypal

        rbMasterCard.setOnClickListener {
           checkFragment(MasterCardFragment())
        }

        rbBanking.setOnClickListener {
            checkFragment(BankingFragment())
        }

        rbPayPal.setOnClickListener {
            checkFragment(PayPalFragment())
        }

    }

    fun checkFragment(fragment: Fragment){
        val fragmentOnboardingManager: FragmentManager = childFragmentManager
        val showStartFragment: FragmentTransaction = fragmentOnboardingManager.beginTransaction()

        val nowFragment = fragmentOnboardingManager.findFragmentById(R.id.cv_fragment_payment)
        if (nowFragment != null) {
            showStartFragment.remove(nowFragment)
        }
        binding.cvFragmentPayment.visibility = View.VISIBLE
//        binding.btnSavePayment.visibility = View.VISIBLE
        showStartFragment.replace(R.id.cv_fragment_payment, fragment)
        showStartFragment.commit()
    }

    fun birthday(){

        val inputBirthdate = binding.inputBirthdate

        val constraintsBuilder = CalendarConstraints.Builder()
        val maxCalendar = MaterialDatePicker.todayInUtcMilliseconds()
        constraintsBuilder.setEnd(maxCalendar)
        val constraints = constraintsBuilder.build()

        val builder = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Wählen Sie Ihr Geburtsdatum")
            .setCalendarConstraints(constraints)

        val datePicker = builder.build()

        inputBirthdate.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
        }

        datePicker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = selection

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(calendar.time)

            inputBirthdate.setText(formattedDate)
        }

    }
}