package com.example.fuchsartig.ui.user

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.fuchsartig.R
import com.example.fuchsartig.data.model.Profile
import com.example.fuchsartig.databinding.FragmentProfileBinding
import com.example.fuchsartig.ui.ViewModels.AuthViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val authViewModel: AuthViewModel by activityViewModels()

    private val CAMERA_PERMISSION_REQUEST_CODE = 100


    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            authViewModel.uploadImage(uri)
        }
    }


    private var visibility = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.inputProfilEmail.setText(authViewModel.currentUser.value?.email.toString())

        showFragment()
        birthday()
        fillSpinner()

        authViewModel.profileRef.get().addOnSuccessListener {
            val profile = it.toObject(Profile::class.java)
            if (profile != null) {
                val checkProfile = profile.personalData
                if (checkProfile){
                    binding.btnDone.visibility = View.VISIBLE
                } else {
                    binding.btnDone.visibility = View.INVISIBLE
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            visibility()
        }

        binding.btnSaveLoginData.setOnClickListener {

        }

        binding.imageUpload.setOnClickListener {
            getContent.launch("image/*")
        }

        binding.btnSavePersonalData.setOnClickListener {
            personalData()
        }

        authViewModel.profileRef.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null) {
                val updatedProfile = snapshot.toObject(Profile::class.java)
                binding.inputGender.setText(updatedProfile?.gender)
                binding.inputFirstName.setText(updatedProfile?.firstName)
                binding.inputLastName.setText(updatedProfile?.lastName)
                binding.inputBirthdate.setText(updatedProfile?.birthdate)
                binding.inputCity.setText(updatedProfile?.city)
                binding.inputHausNumber.setText(updatedProfile?.hausNr)
                binding.inputCountry.setText(updatedProfile?.country)
                binding.inputStreet.setText(updatedProfile?.street)
                binding.inputPlz.setText(updatedProfile?.plz)
                if (updatedProfile?.profileImg != "") {
                    binding.imgProfil.load(updatedProfile?.profileImg)
                }
            }
        }

        binding.btnCamera.setOnClickListener {
            takePhoto()
        }

        binding.btnDropDownLogin.setOnClickListener {
            dropDownLogin()
        }

        binding.btnDropDown.setOnClickListener {
            dropDownPersonalData()
        }

        binding.btnDropDownPayment.setOnClickListener {
            dropDownPayment()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        authViewModel.setPhotoAsImage(requestCode, resultCode, data)
    }
    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            authViewModel.takePhoto(requireActivity())
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            authViewModel.takePhoto(requireActivity())
        }
    }

    private fun takePhoto() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission()
        } else {
            authViewModel.takePhoto(requireActivity())
        }
    }


    private fun visibility() {

        if (!visibility) {
            visibility = true
            binding.cvPayment.visibility = View.VISIBLE
            binding.cvPersonalData.visibility = View.VISIBLE
            binding.cvPersonalLoginData.visibility = View.VISIBLE
        } else {
            visibility = false
            binding.cvPayment.visibility = View.GONE
            binding.cvPersonalData.visibility = View.GONE
            binding.cvPersonalLoginData.visibility = View.GONE

        }

    }

    fun personalData(){
        val genderSelected = binding.inputGender.text.toString()
        val firstName = binding.inputFirstName.text.toString()
        val lastName = binding.inputLastName.text.toString()
        val birth = binding.inputBirthdate.text.toString()
        val city = binding.inputCity.text.toString()
        val hausNr = binding.inputHausNumber.text.toString()
        val country = binding.inputCountry.text.toString()
        val street = binding.inputStreet.text.toString()
        val plz = binding.inputPlz.text.toString()
        var personalData = false

        if (genderSelected != "" && firstName != "" && lastName != "" && birth != "" && city != "" && hausNr != "" && country != "" && street != "" && plz != ""){
            personalData = true
        } else {
            false
        }

        if (personalData){
            binding.btnDone.visibility = View.VISIBLE
        } else {
            binding.btnDone.visibility = View.INVISIBLE
        }

        val profile = Profile(
            gender = genderSelected,
            firstName = firstName,
            lastName = lastName,
            birthdate = birth,
            city = city,
            hausNr = hausNr,
            country = country,
            street = street,
            plz = plz,
            personalData = personalData
        )

        authViewModel.updateProfile(profile)

        dropDownPersonalData()
    }

    private fun dropDownLogin() {
        if (binding.cvPersonalLoginData.visibility == View.GONE) {
            binding.btnDropDownLogin.setImageResource(R.drawable.ic_drop_up)
            binding.cvPersonalLoginData.visibility = View.VISIBLE
        } else {
            binding.btnDropDownLogin.setImageResource(R.drawable.ic_drop_down)
            binding.cvPersonalLoginData.visibility = View.GONE
        }
    }

    private fun dropDownPersonalData() {
        if (binding.cvPersonalData.visibility == View.GONE) {
            binding.btnDropDown.setImageResource(R.drawable.ic_drop_up)
            binding.cvPersonalData.visibility = View.VISIBLE
        } else {
            binding.btnDropDown.setImageResource(R.drawable.ic_drop_down)
            binding.cvPersonalData.visibility = View.GONE
        }
    }

    private fun dropDownPayment() {
        if (binding.cvPayment.visibility == View.GONE) {
            binding.btnDropDownPayment.setImageResource(R.drawable.ic_drop_up)
            binding.cvPayment.visibility = View.VISIBLE

        } else {
            binding.btnDropDownPayment.setImageResource(R.drawable.ic_drop_down)
            binding.cvPayment.visibility = View.GONE
            binding.cvFragmentPayment.visibility = View.GONE
            binding.rbGroup.clearCheck()
        }
    }

    private fun showFragment() {
        val rbMasterCard = binding.rbMastercard
        val rbBanking = binding.rbBanking
        val rbPayPal = binding.rbPaypal

        rbMasterCard.setOnClickListener {
            checkFragment(MasterCardFragment())
        }

        rbBanking.setOnClickListener {
            checkFragment(BankingFragment())
        }

        rbPayPal.setOnClickListener {
            checkFragment(PayPalFragment())
        }

    }

    fun birthday(): String {

        val inputBirthdate = binding.inputBirthdate

        val constraintsBuilder = CalendarConstraints.Builder()
        val maxCalendar = MaterialDatePicker.todayInUtcMilliseconds()
        constraintsBuilder.setEnd(maxCalendar)
        val constraints = constraintsBuilder.build()

        val builder = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Wählen Sie Ihr Geburtsdatum")
            .setCalendarConstraints(constraints)

        val datePicker = builder.build()

        var formattedDate = ""

        inputBirthdate.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
        }

        datePicker.addOnPositiveButtonClickListener { selection ->
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.timeInMillis = selection

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            formattedDate = dateFormat.format(calendar.time)

            inputBirthdate.setText(formattedDate)
        }

        return formattedDate

    }

    private fun checkFragment(fragment: Fragment) {
        val fragmentOnboardingManager: FragmentManager = childFragmentManager
        val showStartFragment: FragmentTransaction = fragmentOnboardingManager.beginTransaction()

        val nowFragment = fragmentOnboardingManager.findFragmentById(R.id.cv_fragment_payment)
        if (nowFragment != null) {
            showStartFragment.remove(nowFragment)
        }
        binding.cvFragmentPayment.visibility = View.VISIBLE
        showStartFragment.replace(R.id.cv_fragment_payment, fragment)
        showStartFragment.commit()
    }

    private fun fillSpinner() {

        val spinnerList = mutableListOf("Anrede", "Herr", "Frau")

        var selectedOption = ""

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            spinnerList
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = adapter

        binding.spinnerGender.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedOption = spinnerList[position]
                    authViewModel.selectedGender.value = selectedOption
                    if (authViewModel.selectedGender.value != "Anrede") {
                        binding.inputGender.setText(authViewModel.selectedGender.value)
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

            }
    }
}