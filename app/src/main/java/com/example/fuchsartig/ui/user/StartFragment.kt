package com.example.fuchsartig.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
    ): View? {
        binding = FragmentStartBinding.inflate(inflater, container, false)

        val switchView = binding.switchCompat



        // Beim Start wird das LoginFragment angezeigt
        val fragmentManager: FragmentManager = childFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.cv_fragment_start, LoginFragment())
        transaction.commit()

        switchView.setOnCheckedChangeListener { _, isChecked ->
            val fragment = if (isChecked) SingupFragment() else LoginFragment()


            val transaction: FragmentTransaction = fragmentManager.beginTransaction()

            transaction.setCustomAnimations(
                R.anim.flip_enter,
                R.anim.flip_exit,
                R.anim.flip_pop_enter,
                R.anim.flip_pop_exit
            )

            transaction.replace(R.id.cv_fragment_start, fragment)
            transaction.addToBackStack(null)
            transaction.commit()

        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnVisitor.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}