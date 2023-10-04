package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.fuchsartig.R
import com.example.fuchsartig.data.model.Banking
import com.example.fuchsartig.data.model.MasterCard
import com.example.fuchsartig.data.model.PayPal
import com.example.fuchsartig.databinding.FragmentPayPalBinding
import com.example.fuchsartig.ui.ViewModels.AuthViewModel


class PayPalFragment : Fragment() {

   private lateinit var binding: FragmentPayPalBinding

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentPayPalBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPaypal.setOnClickListener {

            val email = binding.inputMail.text.toString()
            val password = binding.inputPassword.text.toString()


            val updatedPaypal = PayPal("paypal",email, password)

            authViewModel.updatePaypal(updatedPaypal)
        }

        authViewModel.paypalRef.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null) {
                val paypal = snapshot.toObject(PayPal::class.java)

                if (paypal != null) {
                    binding.inputMail.setText(paypal.email)
                    binding.inputPassword.setText(paypal.password)


                }
            }
        }
    }
}