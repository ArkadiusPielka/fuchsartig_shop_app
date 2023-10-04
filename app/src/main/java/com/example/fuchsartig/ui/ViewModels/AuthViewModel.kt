package com.example.fuchsartig.ui.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fuchsartig.data.model.Banking
import com.example.fuchsartig.data.model.MasterCard
import com.example.fuchsartig.data.model.PayPal
import com.example.fuchsartig.data.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

//import com.google.firebase.storage.FirebaseStorage

class AuthViewModel : ViewModel() {


    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()
//    private val firebaseStorage = FirebaseStorage.getInstance()

    val selectedGender = MutableLiveData<String>()


    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser


    lateinit var profileRef: DocumentReference

    lateinit var paymentRef: DocumentReference

    lateinit var mastercardRef: DocumentReference

    lateinit var bankingRef: DocumentReference

    lateinit var paypalRef: DocumentReference

    lateinit var favoritesRef: DocumentReference

//    private val storageRef = firebaseStorage.reference

    init {
        if (firebaseAuth.currentUser != null) {
            setupUserEnv()
        }
    }

    fun singUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    setupUserEnv()
                    setupNewProfile()
                } else {
                    Log.e("REGISER", "${authResult.exception}")
                }
            }
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { loginResult ->
                if (loginResult.isSuccessful) {
                    setupUserEnv()
                } else {
                    Log.e("LOGIN", "${loginResult.exception}")
                }
            }
    }

    fun setupNewProfile() {
        profileRef.set(Profile())

        setPayment()
    }

    fun updateProfile(profile: Profile) {
        profileRef.set(profile)
    }

    fun setPayment() {
        val paymentRef = profileRef.collection("payment")

        mastercardRef = paymentRef.document("mastercard")
        mastercardRef.set(MasterCard())

        bankingRef = paymentRef.document("banking")
        bankingRef.set(Banking())

        paypalRef = paymentRef.document("paypal")
        paypalRef.set(PayPal())
    }

    fun loadPayment() {
        val paymentRef = profileRef.collection("payment")

        mastercardRef = paymentRef.document("mastercard")
        bankingRef = paymentRef.document("banking")
        paypalRef = paymentRef.document("paypal")
    }

    fun setFavorites(){
        val favoriteRef = profileRef.collection("favorites")
    }

    fun setupUserEnv() {
        _currentUser.value = firebaseAuth.currentUser
        profileRef = firebaseStore.collection("profile").document(firebaseAuth.currentUser?.uid!!)
        loadPayment()
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }

    fun updatePaymentMethod(payment: String) {
        paymentRef = profileRef.collection("payment").document(payment)

    }

    fun updateMastercard(input: MasterCard) {
        updatePaymentMethod("mastercard")
        paymentRef.set(input)
    }

    fun updateBanking(input: Banking) {
        updatePaymentMethod("banking")
        paymentRef.set(input)
    }

    fun updatePaypal(input: PayPal) {
        updatePaymentMethod("paypal")
        paymentRef.set(input)
    }
}

