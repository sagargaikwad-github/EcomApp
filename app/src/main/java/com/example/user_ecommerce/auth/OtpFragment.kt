package com.example.user_ecommerce.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.user_ecommerce.R
import com.example.user_ecommerce.databinding.FragmentOtpBinding
import com.example.user_ecommerce.databinding.FragmentSignInBinding

class OtpFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(layoutInflater)

        getPhone()
        customisingEnterOTP()
        buttonClicks()


        return binding.root
    }

    private fun buttonClicks() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_otpFragment_to_signInFragment)
        }
    }

    private fun customisingEnterOTP() {
        val editTexts = arrayOf(
            binding.otpET1,
            binding.otpET2,
            binding.otpET3,
            binding.otpET4,
            binding.otpET5,
            binding.otpET6
        )


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

    private fun getPhone() {
        val bundle = arguments
        val phone = bundle?.getString("phone")
        binding.phoneTV.text = phone
    }

}