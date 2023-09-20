package com.example.fuchsartig.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.example.fuchsartig.R
import com.example.fuchsartig.databinding.FragmentStartBinding


class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)

        val switchView = binding.switchCompat

        // Beim Start wird das LoginFragment angezeigt
        val fragmentManager: FragmentManager = childFragmentManager
        val showStartFragment: FragmentTransaction = fragmentManager.beginTransaction()
        showStartFragment.replace(R.id.cv_fragment_start, LoginFragment())
        showStartFragment.commit()

        switchView.setOnCheckedChangeListener { _, isChecked ->
            val fragment = if (isChecked) SingupFragment() else LoginFragment()


            val showFragment: FragmentTransaction = fragmentManager.beginTransaction()

            showFragment.setCustomAnimations(
                R.anim.slide_right,
                R.anim.slide_left
            )

            showFragment.replace(R.id.cv_fragment_start, fragment)
            showFragment.addToBackStack(null)
            showFragment.commit()

        }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnVisitor.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
    }


}