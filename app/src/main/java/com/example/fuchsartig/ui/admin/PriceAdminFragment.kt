package com.example.fuchsartig.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.fuchsartig.R
import com.example.fuchsartig.adapter.HomeAdminAdapter
import com.example.fuchsartig.adapter.PriceAdminAdapter
import com.example.fuchsartig.databinding.FragmentHomeAdminBinding
import com.example.fuchsartig.databinding.FragmentPriceAdminBinding
import com.example.fuchsartig.ui.ViewModels.MainViewModel


class PriceAdminFragment : Fragment() {

    private lateinit var binding: FragmentPriceAdminBinding

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPriceAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addObserver()
    }

    fun addObserver(){
        sharedViewModel.products.observe(viewLifecycleOwner, Observer { products ->

            binding.rvChangePrice.adapter = PriceAdminAdapter(products, sharedViewModel)
        })
    }
}