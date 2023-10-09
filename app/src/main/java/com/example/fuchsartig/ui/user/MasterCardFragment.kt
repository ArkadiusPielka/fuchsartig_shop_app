package com.example.fuchsartig.ui.user

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.fuchsartig.data.model.MasterCard
import com.example.fuchsartig.data.model.Profile
import com.example.fuchsartig.databinding.FragmentMasterCardBinding
import com.example.fuchsartig.ui.ViewModels.AuthViewModel
import java.util.Calendar
import java.util.Locale


class MasterCardFragment(var showBtn: Boolean = true) : Fragment() {

    private lateinit var binding: FragmentMasterCardBinding

    private val authViewModel: AuthViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMasterCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardValid()

        if (!showBtn){
            binding.btnSaveCard.visibility = View.GONE
        } else{
            binding.btnSaveCard.visibility = View.VISIBLE
        }

        binding.cbCardOwner.setOnClickListener {
            if (binding.cbCardOwner.isChecked) {
                authViewModel.profileRef.get().addOnSuccessListener {
                    val profile = it.toObject(Profile::class.java)
                    if (profile != null) {
                        val firstName = profile.firstName.toString()
                        val lastName = profile.lastName.toString()
                        Log.d("Firebase Data", "FirstName: $firstName, LastName: $lastName")
                        binding.inputCardOwner.setText("$firstName $lastName")
                    }
                }
            } else {
                binding.inputCardOwner.text?.clear()
            }
        }

        binding.btnSaveCard.setOnClickListener {

            val cardNumber = binding.inputCardNumber.text.toString()
            val cardDate = binding.inputCardDate.text.toString()
            val cardSaveNumber = binding.inputCardCheckNumber.text.toString()
            val cardOwner = binding.inputCardOwner.text.toString()

            val updatedMasterCard = MasterCard("mastercard",cardOwner, cardNumber, cardDate, cardSaveNumber)

            authViewModel.updateMastercard(updatedMasterCard)
        }

        authViewModel.mastercardRef.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null) {
                val masterCard = snapshot.toObject(MasterCard::class.java)
                Log.d("mastercard", "$masterCard")
                if (masterCard != null) {
                    binding.inputCardNumber.setText(masterCard.cardNumber)
                    binding.inputCardDate.setText(masterCard.cardValid)
                    binding.inputCardCheckNumber.setText(masterCard.cardSecurityNumber)
                    binding.inputCardOwner.setText(masterCard.cardOwner)
                }
            }
        }


    }

    private fun cardValid() {
        val inputCardDate = binding.inputCardDate

        inputCardDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, _ ->
                    val formattedDate = String.format(
                        Locale.getDefault(),
                        "%02d.%04d",
                        selectedMonth + 1,
                        selectedYear
                    )
                    inputCardDate.setText(formattedDate)
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
    }

}