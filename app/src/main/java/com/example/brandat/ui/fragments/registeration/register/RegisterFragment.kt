package com.example.brandat.ui.fragments.registeration.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.brandat.databinding.FragmentRegisterBinding
import com.example.brandat.models.Customer

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

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
        val email = binding.emailEt.text.toString()
        val name = binding.firstNameEt.text.toString()
        val phone = binding.numberEt.text.toString()
        val pass = binding.passwordEt.text.toString()

        val customer = Customer(email = email, firstName = name, phone = phone, note = pass)
       // registerViewModel.registerCustomer(customer)

    }

}