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
//        if (binding.inputFirstName.text?.isNotEmpty() ?:  && binding.inputLastName.text.isNotEmpty()){
//
//        }
        binding.btnDropDown.setOnClickListener {
            if (binding.inputPersonalData.visibility == View.GONE) {
                binding.btnDropDown.setImageResource(R.drawable.ic_drop_up)
                binding.inputPersonalData.visibility = View.VISIBLE
            } else {
                binding.btnDropDown.setImageResource(R.drawable.ic_drop_down)
                binding.inputPersonalData.visibility = View.GONE
            }
        }
    }
}