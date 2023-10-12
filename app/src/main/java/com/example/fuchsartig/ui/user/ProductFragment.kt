package com.example.fuchsartig.ui.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fuchsartig.R
import com.example.fuchsartig.adapter.GridAdapter
import com.example.fuchsartig.adapter.LinearAdapter
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.databinding.FragmentProductBinding
import com.example.fuchsartig.ui.ViewModels.ApiLayoutStatus
import com.example.fuchsartig.ui.ViewModels.AuthViewModel
import com.example.fuchsartig.ui.ViewModels.MainViewModel


class ProductFragment : Fragment() {


    private lateinit var binding: FragmentProductBinding

    private val sharedViewModel: MainViewModel by activityViewModels()

    private val authViewModel: AuthViewModel by activityViewModels()


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
        sharedViewModel.setApiLayoutStatus(ApiLayoutStatus.LINEAR)
        setupButtons()
        addObserver()


    }

    private fun addObserver() {

        sharedViewModel.products.observe(viewLifecycleOwner, Observer { products ->

            sharedViewModel.layout.observe(viewLifecycleOwner, Observer { status ->

                when (status) {

                    ApiLayoutStatus.LINEAR -> {

                        binding.rvProduct.layoutManager = LinearLayoutManager(requireContext())
                        binding.rvProduct.adapter =
                            LinearAdapter(products, sharedViewModel, authViewModel)

                        binding.cvSortVertical.setCardBackgroundColor(
                            ContextCompat.getColor(requireContext(), R.color.primary_color)
                        )
                        binding.cvSortHorizontal.setCardBackgroundColor(
                            ContextCompat.getColor(requireContext(), R.color.white)
                        )
                    }

                    ApiLayoutStatus.GRID -> {

                        binding.rvProduct.layoutManager = GridLayoutManager(requireContext(), 2)
                        binding.rvProduct.adapter =
                            GridAdapter(products, sharedViewModel, authViewModel)

                        binding.cvSortVertical.setCardBackgroundColor(
                            ContextCompat.getColor(requireContext(), R.color.white)
                        )
                        binding.cvSortHorizontal.setCardBackgroundColor(
                            ContextCompat.getColor(requireContext(), R.color.primary_color)
                        )
                    }
                }
            })
        })
    }

    private fun setupButtons() {

        val slideRight = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_right)

        binding.cvSortVertical.setOnClickListener {

            sharedViewModel.setApiLayoutStatus(ApiLayoutStatus.LINEAR)
            binding.rvProduct.startAnimation(slideRight)

        }

        binding.cvSortHorizontal.setOnClickListener {

            sharedViewModel.setApiLayoutStatus(ApiLayoutStatus.GRID)
            binding.rvProduct.startAnimation(slideRight)

        }

        binding.btnSearch.setOnClickListener { view ->
            val popupMenu = PopupMenu(requireActivity(), view)
            val menuInflater = popupMenu.menuInflater
            menuInflater.inflate(R.menu.filter_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.item_all -> {
                        val cards = sharedViewModel.products.value
                        if (sharedViewModel.layout.value == ApiLayoutStatus.LINEAR) {
                            binding.rvProduct.adapter =
                                cards?.let { LinearAdapter(it, sharedViewModel, authViewModel) }
                        } else {
                            binding.rvProduct.adapter =
                                cards?.let { GridAdapter(it, sharedViewModel, authViewModel) }
                        }
                        return@setOnMenuItemClickListener true
                    }

                    R.id.item_cards -> {
                        val cards = sharedViewModel.products.value?.filter { product ->  product.category == "CARDS" }
                        if (sharedViewModel.layout.value == ApiLayoutStatus.LINEAR) {
                            binding.rvProduct.adapter =
                                cards?.let { LinearAdapter(it, sharedViewModel, authViewModel) }
                        } else {
                            binding.rvProduct.adapter =
                                cards?.let { GridAdapter(it, sharedViewModel, authViewModel) }
                        }
                        return@setOnMenuItemClickListener true
                    }

                    R.id.item_boxes -> {
                        val cards = sharedViewModel.products.value?.filter { product ->  product.category == "BOXES" }
                        if (sharedViewModel.layout.value == ApiLayoutStatus.LINEAR) {
                            binding.rvProduct.adapter =
                                cards?.let { LinearAdapter(it, sharedViewModel, authViewModel) }
                        } else {
                            binding.rvProduct.adapter =
                                cards?.let { GridAdapter(it, sharedViewModel, authViewModel) }
                        }
                        return@setOnMenuItemClickListener true
                    }
                    R.id.item_bags -> {
                        val cards = sharedViewModel.products.value?.filter { product ->  product.category == "BAGS" }
                        if (sharedViewModel.layout.value == ApiLayoutStatus.LINEAR) {
                            binding.rvProduct.adapter =
                                cards?.let { LinearAdapter(it, sharedViewModel, authViewModel) }
                        } else {
                            binding.rvProduct.adapter =
                                cards?.let { GridAdapter(it, sharedViewModel, authViewModel) }
                        }
                        return@setOnMenuItemClickListener true
                    }
                    R.id.item_books -> {
                        val cards = sharedViewModel.products.value?.filter { product ->  product.category == "BOOKS" }
                        if (sharedViewModel.layout.value == ApiLayoutStatus.LINEAR) {
                            binding.rvProduct.adapter =
                                cards?.let { LinearAdapter(it, sharedViewModel, authViewModel) }
                        } else {
                            binding.rvProduct.adapter =
                                cards?.let { GridAdapter(it, sharedViewModel, authViewModel) }
                        }
                        return@setOnMenuItemClickListener true
                    }

                    else -> return@setOnMenuItemClickListener false
                }
            }
            popupMenu.show()
        }
    }
}