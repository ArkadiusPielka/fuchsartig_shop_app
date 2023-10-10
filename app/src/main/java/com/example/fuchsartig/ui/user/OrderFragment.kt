package com.example.fuchsartig.ui.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.fuchsartig.R
import com.example.fuchsartig.adapter.OrderAdapter
import com.example.fuchsartig.adapter.ShopCartAdapter
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.data.model.ProductNumberUpdate
import com.example.fuchsartig.data.model.Profile
import com.example.fuchsartig.databinding.FragmentOrderBinding
import com.example.fuchsartig.ui.ViewModels.AuthViewModel
import com.example.fuchsartig.ui.ViewModels.MainViewModel


class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding

    private val authViewModel: AuthViewModel by activityViewModels()

    private val sharedViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFragment()
        enableText()
        addObserver()
    }

    private fun addObserver() {
        authViewModel.profileRef.get().addOnSuccessListener {
            val profile = it.toObject(Profile::class.java)
            if (profile != null) {
                val firstName = profile.firstName.toString()
                val lastName = profile.lastName.toString()
                Log.d("Firebase Data", "FirstName: $firstName, LastName: $lastName")
                binding.inputName.setText("$firstName $lastName")
                binding.inputNameDelivery.setText("$firstName $lastName")
            }
        }
        authViewModel.profileRef.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null) {
                val updatedProfile = snapshot.toObject(Profile::class.java)
                binding.inputGender.setText(updatedProfile?.gender)
                binding.inputCity.setText(updatedProfile?.city)
                binding.inputHausNumber.setText(updatedProfile?.hausNr)
                binding.inputCountry.setText(updatedProfile?.country)
                binding.inputStreet.setText(updatedProfile?.street)
                binding.inputPlz.setText(updatedProfile?.plz)
            }
        }
        authViewModel.mastercardRef.get().addOnSuccessListener {
            if (it.exists()) {
                val masterCardCheck = it.getBoolean("masterCardCheck")
                if (masterCardCheck == true) {
                    binding.rbMastercard.visibility = View.VISIBLE
                } else {
                    binding.rbMastercard.visibility = View.GONE
                }
            }
        }

        authViewModel.bankingRef.get().addOnSuccessListener {
            if (it.exists()) {
                val bankingCheck = it.getBoolean("bankingCheck")
                if (bankingCheck == true) {
                    binding.rbBanking.visibility = View.VISIBLE
                } else {
                    binding.rbBanking.visibility = View.GONE
                }
            }
        }

        authViewModel.paypalRef.get().addOnSuccessListener {
            if (it.exists()) {
                val paypalCheck = it.getBoolean("paypalCheck")
                if (paypalCheck == true) {
                    binding.rbPaypal.visibility = View.VISIBLE
                } else {
                    binding.rbPaypal.visibility = View.GONE
                }
            }
        }

        authViewModel.shoppingRef.addSnapshotListener { value, error ->
            if (error == null && value != null) {

                val listProduct = authViewModel.buyingProducts
                val price = authViewModel.totalPrice

                binding.rvOrder.adapter =
                    OrderAdapter(listProduct, sharedViewModel, authViewModel)
                sharedViewModel.updateLayout()
                binding.tvTotalPrice.text = String.format("%.2f".format(price))
            }
        }

        binding.btnBuy.isEnabled = false

        binding.checkAgb.setOnClickListener {
            binding.btnBuy.isEnabled = binding.checkAgb.isChecked
        }

        binding.btnBuy.setOnClickListener {
            authViewModel.shoppingRef.addSnapshotListener { value, error ->
                if (error == null && value != null) {
                    val selectedProducts = authViewModel.buyingProducts
                    sharedViewModel.updateProductNumber(selectedProducts)
                    val collectionRef = authViewModel.shoppingRef
                    collectionRef.get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                document.reference.delete()
                            }

                        }
                }

            }
            val navController = findNavController()
            navController.popBackStack()
            navController.navigate(R.id.navigation_home)
        }

    }

    private fun showFragment() {
        val rbMasterCard = binding.rbMastercard
        val rbBanking = binding.rbBanking
        val rbPayPal = binding.rbPaypal

        rbMasterCard.setOnClickListener {
            checkFragment(MasterCardFragment(false))
        }

        rbBanking.setOnClickListener {
            checkFragment(BankingFragment())
        }

        rbPayPal.setOnClickListener {
            checkFragment(PayPalFragment())
        }

    }

    private fun checkFragment(fragment: Fragment) {
        val fragmentOnboardingManager: FragmentManager = childFragmentManager
        val showStartFragment: FragmentTransaction =
            fragmentOnboardingManager.beginTransaction()

        val nowFragment = fragmentOnboardingManager.findFragmentById(R.id.cv_fragment_payment)
        if (nowFragment != null) {
            showStartFragment.remove(nowFragment)
        }
        binding.cvFragmentPayment.visibility = View.VISIBLE
        showStartFragment.replace(R.id.cv_fragment_payment, fragment)
        showStartFragment.commit()
    }

    private fun enableText() {
        binding.inputGender.isEnabled = false
        binding.inputCity.isEnabled = false
        binding.inputHausNumber.isEnabled = false
        binding.inputCountry.isEnabled = false
        binding.inputStreet.isEnabled = false
        binding.inputPlz.isEnabled = false
        binding.inputName.isEnabled = false
        binding.inputNameDelivery.isEnabled = false
    }
}