package com.example.user_ecommerce.auth

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.user_ecommerce.Utils
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

class AuthViewModel : ViewModel() {

    //MutableStateFlow Works same as LiveData. not as Differences in that.

    //In ViewModels for Local variables we added _VariableName like prefix.

    //VerificationID for save _verificationID whenever we required.
    private val _verificationID = MutableStateFlow<String?>(null)

    //_otpSent boolean For getting Call for OTP Sent Callback
    private val _otpSent = MutableStateFlow<Boolean?>(false)
    val otpSent = _otpSent

    //_isSignInSuccessful boolean For getting Call for SignInSuccess Callback
    private val _isSignInSuccessful = MutableStateFlow<Boolean?>(false)
    val isSignInSuccessful = _isSignInSuccessful

    //sendOtp we call directly from OnCreate. Means we send phoneNumber to firebase.
    fun sendOtp(phoneNumber: String, activity: Activity) {

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            }

            override fun onVerificationFailed(e: FirebaseException) {
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                _verificationID.value = verificationId
                _otpSent.value = true
            }
        }

        val options = PhoneAuthOptions.newBuilder(Utils.getInstance())
            .setPhoneNumber("+91$phoneNumber") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    //SignInFunction, we call this with OTP
    fun signInWithPhoneAuthCredential(userOTP: String) {
        val credential = PhoneAuthProvider.getCredential(_verificationID.value.toString(), userOTP)
        Utils.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _isSignInSuccessful.value = true
                } else {

                }
            }
    }

}