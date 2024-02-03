package com.example.user_ecommerce.auth

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.user_ecommerce.R
import com.example.user_ecommerce.Utils
import com.example.user_ecommerce.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(layoutInflater)

        editTextConditions()
        continueButtonClick()

        return binding.root
    }

    private fun editTextConditions() {


        binding.signINPhoneET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //After 10 digits we changed buttonColor.
                if (p0?.length == 10) {
                    binding.signINContinueBTN.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.yellow
                        )
                    )
                } else {
                    binding.signINContinueBTN.setBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grey_button_bg
                        )
                    )
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    private fun continueButtonClick() {

        //Sending phoneNumber to next Activity.
        binding.signINContinueBTN.setOnClickListener {
            val phoneNumber = binding.signINPhoneET.text.toString()

            if (phoneNumber.length == 10) {
                val bundle = Bundle()
                bundle.putString("phone", phoneNumber)
                findNavController().navigate(R.id.action_signInFragment_to_otpFragment, bundle)
            } else {
                Utils.showToast(requireContext(), "Invalid Phone Number")
            }
        }
    }

}