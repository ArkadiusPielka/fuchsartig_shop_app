package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.fuchsartig.R
import com.example.fuchsartig.data.model.Profile
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

        if (authViewModel.currentUser.value?.uid == null) {
            binding.btnLogOut.visibility = View.GONE
            binding.btnProfil.visibility = View.GONE
            binding.btnLogIn.visibility = View.VISIBLE
        } else {
            if (authViewModel.isAdmin.value == true) {

                binding.btnLogOut.visibility = View.VISIBLE
                binding.btnProfil.visibility = View.GONE
                binding.btnLogIn.visibility = View.GONE
            } else {

                binding.btnLogOut.visibility = View.VISIBLE
                binding.btnProfil.visibility = View.VISIBLE
                binding.btnLogIn.visibility = View.GONE
            }
        }

        binding.btnLogOut.setOnClickListener {
            findNavController().navigate(R.id.navigation_login)
            authViewModel.isAdmin.postValue(false)
            authViewModel.logout()
        }

        binding.btnLogIn.setOnClickListener {
            findNavController().navigate(R.id.navigation_login)
        }

        binding.btnProfil.setOnClickListener {
            findNavController().navigate(R.id.navigation_profil)
        }

        authViewModel.profileRef.get().addOnSuccessListener {
            val profile = it.toObject(Profile::class.java)
            if (profile != null) {
                if (profile?.profileImg != "") {
                    binding.imgProfil.load(profile?.profileImg)
                }
            }
        }
    }

}