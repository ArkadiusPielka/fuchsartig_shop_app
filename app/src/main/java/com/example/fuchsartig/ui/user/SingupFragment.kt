package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fuchsartig.R
import com.example.fuchsartig.databinding.FragmentSignupBinding
import com.example.fuchsartig.ui.ViewModels.AuthViewModel
import com.example.fuchsartig.ui.ViewModels.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class SingupFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {

            val email = binding.inputMail.text.toString()
            val password = binding.inputPassword.text.toString()

            if (!authViewModel.checkEmailFormat(email)) {
                authViewModel.wrongEmailFormat(requireContext())
            }else if (password.length < 6){
                authViewModel.passwortLenght(requireContext())
            }else if (email == "" || password == "") {
                registerMessageEmptyInput()
            } else {
                authViewModel.singUp(email, password)
                registerMessage()
            }
        }
    }

    private fun registerMessage() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Vielen Dank für deine Registrierung")
            .setMessage("Es fehlen noch weitere Angaben, die können Sie jetzt oder später machen.")
            .setCancelable(false)
            .setNegativeButton("Später") { _, _ ->
                findNavController().navigate(R.id.navigation_home)

            }
            .setPositiveButton("Jetzt") { _, _ ->
                findNavController().navigate(R.id.navigation_register)
            }
            .show()
    }

    private fun registerMessageEmptyInput() {
        if (binding.inputMail.text.isNullOrEmpty() && binding.inputPassword.text.isNullOrEmpty()) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Es fehlen Angaben")
                .setMessage("E-Mail und Password nicht eingetragen")
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->

                }
                .show()
        } else if (binding.inputMail.text.isNullOrEmpty()){
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Es fehlen Angaben")
                .setMessage("E-Mail nicht eingetragen")
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->

                }
                .show()
        }else if (binding.inputPassword.text.isNullOrEmpty()){
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Es fehlen Angaben")
                .setMessage("Password nicht eingetragen")
                .setCancelable(false)
                .setPositiveButton("OK") { _, _ ->

                }
                .show()
        }
    }
}