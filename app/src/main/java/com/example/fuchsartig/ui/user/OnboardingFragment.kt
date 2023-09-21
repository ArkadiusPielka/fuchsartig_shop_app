package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fuchsartig.R
import com.example.fuchsartig.databinding.FragmentOnboardingBinding

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstName = binding.inputFirstName.text.toString().trim()
        val lastName = binding.inputLastName.text.toString().trim()

        binding.btnSavePersonalData.setOnClickListener {
            if (firstName != "" && lastName != ""){
                binding.inputFirstName.setText("")
                binding.inputLastName.setText("")
                binding.btnDone.visibility = View.VISIBLE
            } else {
                binding.btnDone.visibility = View.INVISIBLE
            }
            binding.btnDropDown.setImageResource(R.drawable.ic_drop_down)
            binding.inputPersonalData.visibility = View.GONE
        }


        binding.btnDropDown.setOnClickListener {
            if (binding.inputPersonalData.visibility == View.GONE) {
                binding.btnDropDown.setImageResource(R.drawable.ic_drop_up)
                binding.inputPersonalData.visibility = View.VISIBLE
            } else {
                binding.btnDropDown.setImageResource(R.drawable.ic_drop_down)
                binding.inputPersonalData.visibility = View.GONE
            }
        }

        binding.btnDropDownPayment.setOnClickListener {
            if (binding.inputPayment.visibility == View.GONE) {
                binding.btnDropDownPayment.setImageResource(R.drawable.ic_drop_up)
                binding.inputPayment.visibility = View.VISIBLE
            } else {
                binding.btnDropDownPayment.setImageResource(R.drawable.ic_drop_down)
                binding.inputPayment.visibility = View.GONE
            }
        }
    }
}