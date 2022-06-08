package com.example.brandat.ui.fragments.registeration.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.brandat.databinding.FragmentRegisterBinding
import com.example.brandat.models.Customer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var email: String
    private lateinit var name: String
    private lateinit var phone: String
    private lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        email = binding.emailEt.text.toString()
        name = binding.firstNameEt.text.toString()
        phone = binding.numberEt.text.toString()
        pass = binding.passwordEt.text.toString()
        binding.registerBtn.setOnClickListener {

            if (email.isEmpty()) {
                binding.firstNameEt.requestFocus()
                binding.firstNameEt.error="Required"
            }
            if(name.isEmpty()){

            }
            if(phone.isEmpty()){

            }
            if(pass.isEmpty()){
                
            }
            Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
            val customer = Customer(email = email, firstName = name, phone = phone, note = pass)
            registerViewModel.registerCustomer(customer)
            registerViewModel.signUpSuccess.observe(viewLifecycleOwner) {
                Log.e("TAG", "=======:$it")
                if (it == true) {
                    Toast.makeText(requireContext(), "Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Try Again", Toast.LENGTH_SHORT).show()

                }
            }
        }


    }

    private fun signUp() {

    }

}