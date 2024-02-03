package com.example.user_ecommerce.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.user_ecommerce.R
import com.example.user_ecommerce.Utils
import com.example.user_ecommerce.databinding.FragmentOtpBinding
import kotlinx.coroutines.launch

class OtpFragment : Fragment() {
    private lateinit var binding: FragmentOtpBinding
    private lateinit var viewModel: AuthViewModel
    private var phone: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)


        getPhone()
        customisingEnterOTP()
        buttonClicks()
        sendOTP()

        return binding.root
    }

    private fun sendOTP() {
        Utils.showDialog(requireContext(), "OTP Sending...")

        if (phone != null) {


//            lifecycleScope.launch {
//                if (viewModel.otpSent.collect() {
//                        Utils.showToast(requireContext(), "OTP Sent Successfully")
//                    }) else {
//                    Utils.showToast(requireContext(), "OTP Sent Successfully")
//                }
//            }

            viewModel.apply {
                sendOtp(phone!!, requireActivity())
                lifecycleScope.launch {
                    otpSent.collect {
                        if (it == true) {
                            Utils.hideDialog()
                            Utils.showToast(requireContext(), "OTP sent successfully")
                        }
                    }
                }
            }

        } else {
            Utils.showToast(requireContext(), "Unknown error occurred!!")
        }

    }

    private fun buttonClicks() {

        //Toolbar back Click
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_otpFragment_to_signInFragment)
        }


        //LoginButton Click
        binding.loginBTN.setOnClickListener {
            val editTexts = arrayOf(
                binding.otpET1,
                binding.otpET2,
                binding.otpET3,
                binding.otpET4,
                binding.otpET5,
                binding.otpET6
            )

            //This otp variable joins all array data in one variable.
            val otp = editTexts.joinToString("") { it.text.toString() }

            if (otp.length < 6) {
                //entered Otp less than 6, Invalid OTP
                Utils.showToast(requireContext(), "Invalid OTP")
            } else {
                Utils.showDialog(requireContext(), "Signing In...")
                verifyOTP(otp)
            }
        }
    }

    private fun verifyOTP(otp: String) {
        viewModel.signInWithPhoneAuthCredential(otp)

        lifecycleScope.launch {
            //Collect because we used mutableState Flow, and mutableState Flow are like coroutines.
            viewModel.isSignInSuccessful.collect {
                if (it == true) {
                    Utils.hideDialog()
                    Utils.showToast(requireContext(), "SignIn Success")
                }
            }
        }
    }

    private fun customisingEnterOTP() {

        //Made array of editTexts
        val editTexts = arrayOf(
            binding.otpET1,
            binding.otpET2,
            binding.otpET3,
            binding.otpET4,
            binding.otpET5,
            binding.otpET6
        )

        //Checked value of editTexts, this will help us to add/remove OTP with requestFocus.
        for (i in editTexts.indices) {
            editTexts[i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0?.length == 1) {
                        if (i < editTexts.size - 1) {
                            editTexts[i + 1].requestFocus()
                        }
                    } else if (p0?.length == 0) {
                        if (i > 0) {
                            editTexts[i - 1].requestFocus()
                        }
                    }
                }
            })
        }
    }


    //Get Bundle Data
    private fun getPhone() {
        val bundle = arguments
        phone = bundle?.getString("phone")
        binding.phoneTV.text = phone
    }

}