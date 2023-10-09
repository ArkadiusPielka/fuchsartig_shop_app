package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fuchsartig.R
import com.example.fuchsartig.databinding.FragmentSettingsBinding
import com.example.fuchsartig.ui.ViewModels.AuthViewModel


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (authViewModel.currentUser.value?.uid == null){
            binding.btnLogOut.visibility = View.GONE
            binding.btnProfil.visibility = View.GONE
            binding.btnLogIn.visibility = View.VISIBLE
        } else {
            binding.btnLogOut.visibility = View.VISIBLE
            binding.btnProfil.visibility = View.VISIBLE
            binding.btnLogIn.visibility = View.GONE
        }

        binding.btnLogOut.setOnClickListener {
            findNavController().navigate(R.id.navigation_login)
            authViewModel.logout()
        }

        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(R.id.navigation_login)
        }

        binding.btnProfil.setOnClickListener {
            findNavController().navigate(R.id.navigation_profil)
        }
    }

}