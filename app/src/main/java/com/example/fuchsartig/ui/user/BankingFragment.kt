package com.example.fuchsartig.ui.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.fuchsartig.R
import com.example.fuchsartig.data.model.Banking
import com.example.fuchsartig.data.model.MasterCard
import com.example.fuchsartig.data.model.Profile
import com.example.fuchsartig.databinding.FragmentBankingBinding
import com.example.fuchsartig.ui.ViewModels.AuthViewModel


class BankingFragment : Fragment() {

    private lateinit var binding: FragmentBankingBinding

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBankingBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cbCardOwner.setOnClickListener {
            if (binding.cbCardOwner.isChecked) {
                authViewModel.profileRef.get().addOnSuccessListener {
                    val profile = it.toObject(Profile::class.java)
                    if (profile != null) {
                        val firstName = profile.firstName.toString()
                        val lastName = profile.lastName.toString()
                        Log.d("Firebase Data", "FirstName: $firstName, LastName: $lastName")
                        binding.inputBankOwner.setText("$firstName $lastName")
                    }
                }
            } else {
                binding.inputBankOwner.text?.clear()
            }
        }

        binding.btnSaveBanking.setOnClickListener {

            val bankOwner = binding.inputBankOwner.text.toString()
            val iban = binding.inputIban.text.toString()
            val bic = binding.inputBic.text.toString()

            val updatedBanking = Banking("banking",bankOwner, iban, bic)

            authViewModel.updateBanking(updatedBanking)
        }

        authViewModel.bankingRef.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null) {
                val banking = snapshot.toObject(Banking::class.java)

                if (banking != null) {
                    binding.inputBankOwner.setText(banking.bankingOwner)
                    binding.inputIban.setText(banking.IBAN)
                    binding.inputBic.setText(banking.BIC)

                }
            }
        }
    }
}