package com.example.fuchsartig.ui.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fuchsartig.data.model.Profile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.storage.FirebaseStorage

class AuthViewModel : ViewModel() {


    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()
//    private val firebaseStorage = FirebaseStorage.getInstance()


    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    lateinit var profileRef: DocumentReference

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
//                    logout()
//                    login(email, password)
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
    }

    fun updateProfile(profile: Profile) {
        profileRef.set(profile)
    }

    fun setupUserEnv() {
        _currentUser.value = firebaseAuth.currentUser
        profileRef = firebaseStore.collection("profile").document(firebaseAuth.currentUser?.uid!!)
    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }
}