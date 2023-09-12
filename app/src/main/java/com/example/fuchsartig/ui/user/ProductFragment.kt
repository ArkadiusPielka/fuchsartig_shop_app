package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fuchsartig.R
import com.example.fuchsartig.adapter.GridAdapter
import com.example.fuchsartig.adapter.LinearAdapter
import com.example.fuchsartig.databinding.FragmentProductBinding
import com.example.fuchsartig.ui.ViewModels.ApiLayoutStatus
import com.example.fuchsartig.ui.ViewModels.MainViewModel


class ProductFragment : Fragment() {


    private lateinit var binding: FragmentProductBinding

    private val sharedViewModel: MainViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        addObserver()
    }

    private fun addObserver() {

        sharedViewModel.layout.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                ApiLayoutStatus.LINEAR -> {
                    binding.btnSortVertical.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.primary_color)
                    )
                    binding.btnSortHorizontal.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.transparent)
                    )
                }

                ApiLayoutStatus.GRID -> {
                    binding.btnSortVertical.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.transparent)
                    )
                    binding.btnSortHorizontal.setBackgroundColor(
                        ContextCompat.getColor(requireContext(), R.color.primary_color)
                    )
                }
            }
        })

        sharedViewModel.products.observe(viewLifecycleOwner, Observer {

            val linearLayoutManager = if (sharedViewModel.layout.value == ApiLayoutStatus.LINEAR) {
                LinearLayoutManager(requireContext())
            } else {
                GridLayoutManager(requireContext(), 2)
            }
            binding.rvProduct.layoutManager = linearLayoutManager
            binding.rvProduct.adapter = LinearAdapter(it)

        })
    }

    private fun setupButtons() {

        binding.btnSortVertical.setOnClickListener {
            sharedViewModel.setApiLayoutStatus(ApiLayoutStatus.LINEAR)
            sharedViewModel.products.value?.let { products ->
                binding.rvProduct.layoutManager = LinearLayoutManager(requireContext())
                binding.rvProduct.adapter = LinearAdapter(products)
            }
        }

        binding.btnSortHorizontal.setOnClickListener {
            sharedViewModel.setApiLayoutStatus(ApiLayoutStatus.GRID)
            sharedViewModel.products.value?.let { products ->
                binding.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.rvProduct.adapter = GridAdapter(products)
            }
        }
    }
}