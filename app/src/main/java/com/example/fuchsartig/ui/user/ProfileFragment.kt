package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.fuchsartig.R
import com.example.fuchsartig.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

        private lateinit var binding: FragmentProfileBinding

    private var visibility  = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showFragment()

        binding.btnEdit.setOnClickListener {
            visibility()
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

        binding.btnDropDownLogin.setOnClickListener {
            dropDownLogin()
        }

        binding.btnDropDown.setOnClickListener {
            dropDownPersonalData()
        }

        binding.btnDropDownPayment.setOnClickListener {
            dropDownPayment()
        }
    }

    private fun visibility(){

        if (!visibility) {
            visibility = true
            binding.cvPayment.visibility = View.VISIBLE
            binding.cvPersonalData.visibility = View.VISIBLE
            binding.cvPersonalLoginData.visibility = View.VISIBLE
        } else{
            visibility = false
            binding.cvPayment.visibility = View.GONE
            binding.cvPersonalData.visibility = View.GONE
            binding.cvPersonalLoginData.visibility = View.GONE

        }

    }

    private fun dropDownLogin(){
        if (binding.cvPersonalLoginData.visibility == View.GONE) {
            binding.btnDropDown.setImageResource(R.drawable.ic_drop_up)
            binding.cvPersonalLoginData.visibility = View.VISIBLE
        } else {
            binding.btnDropDown.setImageResource(R.drawable.ic_drop_down)
            binding.cvPersonalLoginData.visibility = View.GONE
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

    private fun checkFragment(fragment: Fragment){
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
}