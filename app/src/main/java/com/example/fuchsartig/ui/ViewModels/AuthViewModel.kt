package com.example.fuchsartig.ui.ViewModels

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.fuchsartig.R
import com.example.fuchsartig.data.model.Banking
import com.example.fuchsartig.data.model.MasterCard
import com.example.fuchsartig.data.model.PayPal
import com.example.fuchsartig.data.model.Product
import com.example.fuchsartig.data.model.Profile
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.withContext

class AuthViewModel : ViewModel() {


    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()
    private val firebaseStorage = FirebaseStorage.getInstance()

    val CAMERA_REQUEST_CODE = 0

    var shoppinCartAmount = 0

    val selectedGender = MutableLiveData<String>()

    var currentPrice = MutableLiveData<String>()

    var totalPrice: Double = 0.0

    var isAdmin = MutableLiveData<Boolean>(false)

    var userLoggin = 0

    var favoriteProducts = mutableListOf<Product>()

    var buyingProducts = mutableListOf<Product>()

    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser


    lateinit var profileRef: DocumentReference

    lateinit var paymentRef: DocumentReference

    lateinit var mastercardRef: DocumentReference

    lateinit var bankingRef: DocumentReference

    lateinit var paypalRef: DocumentReference

    lateinit var favoritesRef: CollectionReference

    lateinit var shoppingRef: CollectionReference

    private val storageRef = firebaseStorage.reference

    init {
        if (firebaseAuth.currentUser != null) {
            setupUserEnv()
        }
    }

    fun checkEmailFormat(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    fun singUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    setupUserEnv()
                    setupNewProfile()
                    userLoggin = 1
                } else {
                    Log.e("REGISER", "${authResult.exception}")
                }
            }


    }


    fun wrongEmailFormat(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setMessage("Die eingegebene E-Mail, hat das falsche Format!")
            .setCancelable(true)
            .show()
    }

    fun passwortLenght(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setMessage("Das eingegebene Passwort muss aud min. 6 zeichen bestehen")
            .setCancelable(true)
            .show()
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { loginResult ->
                if (loginResult.isSuccessful) {
                    setupUserEnv()
                    userLoggin = 1
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


    fun addFavorites(product: Product) {
        favoritesRef.document(product.apiId.toString()).set(product)

    }

    fun removeFavorites(product: Product) {
        favoritesRef.document(product.apiId.toString()).delete()

    }

    private fun loadFavorites() {
        favoriteProducts.clear()

        favoritesRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val product = document.toObject(Product::class.java)
                    favoriteProducts.add(product)
                }
            }

    }

    fun addToCart(product: Product) {

        shoppingRef.document(product.apiId.toString()).set(product)

    }

    fun removeFromCart(product: Product) {
        shoppingRef.document(product.apiId.toString()).delete()
    }

    fun reduceAmount(product: Product) {
        if (product.selectedNumber == 1) {
            removeFromCart(product)
        } else {
            val updateAmount = product.selectedNumber - 1
            val productRef = shoppingRef.document(product.apiId.toString())
            productRef.update("selectedNumber", updateAmount)


        }
    }

    fun addAmount(product: Product) {
        val productAmount = product.number.toInt()
        if (product.selectedNumber == productAmount) {
        } else {
            val updateAmount = product.selectedNumber + 1
            val productRef = shoppingRef.document(product.apiId.toString())
            productRef.update("selectedNumber", updateAmount)


        }
    }


    private fun loadShoppinCart() {
        buyingProducts.clear()

        shoppingRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val product = document.toObject(Product::class.java)
                    buyingProducts.add(product)
                }
            }

    }


    fun setupUserEnv() {
        _currentUser.value = firebaseAuth.currentUser
        profileRef =
            firebaseStore.collection("profile").document(firebaseAuth.currentUser?.uid!!)
        favoritesRef = profileRef.collection("favorites")
        shoppingRef = profileRef.collection("shopping")

        profileRef.get().addOnSuccessListener { profil ->
            if (profil.exists()) {
                val profile = profil.getBoolean("admin") == true

                isAdmin.postValue(profile)
                Log.d(TAG, "$isAdmin")
            } else {
                isAdmin.postValue(false)
            }
            loadShoppinCart()
            loadPayment()
            loadFavorites()
        }

    }

    fun logout() {
        firebaseAuth.signOut()
        _currentUser.value = firebaseAuth.currentUser
    }

    fun sendPasswordRecovery(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
        }
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

    fun uploadImage(uri: Uri) {
        val imageRef = storageRef.child("profileImg/${currentUser.value?.uid}/profileImg")
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnCompleteListener {
            imageRef.downloadUrl.addOnCompleteListener {
                if (it.isSuccessful) {
                    setImage(it.result)
                }
            }
        }
    }

    fun takePhoto(activity: Activity) {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        activity.startActivityForResult(intent, CAMERA_REQUEST_CODE)

    }


    fun setPhotoAsImage(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri = data.data
            Log.d("MyApp", "Image Uri: $imageUri")
            if (imageUri != null) {
                setImage(imageUri)
            }
        }
    }

    private fun setImage(uri: Uri) {
        profileRef.update("profileImg", uri.toString()).addOnFailureListener {
            Log.w("ERROR", "Error writing document: $it")
        }
    }
}

