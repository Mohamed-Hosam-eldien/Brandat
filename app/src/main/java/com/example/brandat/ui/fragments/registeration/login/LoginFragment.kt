package com.example.brandat.ui.fragments.registeration.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.brandat.R
import com.example.brandat.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var email: String
    private lateinit var password: String
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //cheack if  already ==go to profile
        //else==faild try to sign out
        binding.tvNewAccount.setOnClickListener {
            Toast.makeText(requireContext(), "ops", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.registerBtn.setOnClickListener {
            if (checkEmpty()) {
                loginViewModel.loginCustomer("ana.zege2t@gmail.com")
                loginViewModel.signInSuccess.observe(viewLifecycleOwner){
                    Toast.makeText(requireContext(), "ops!$it", Toast.LENGTH_SHORT).show()
                    Log.e("TAG", "aziza '((((( ${it.customer}) ", )
//                    if(it.customer!!.email!=""){
//                        findNavController().navigate(R.id.action_loginFragment_to_profileDataFragment)
//                    }
                }

            }
        }
    }

    //=======================================
    private fun checkEmpty(): Boolean {
        email = binding.etEmail.text.toString()
        password = binding.etPass.text.toString()

        if (email.isEmpty()) {
            binding.etEmail.requestFocus()
            binding.etEmail.error = "Required"
            return false
        }
        if (password.isEmpty()) {
            binding.etPass.requestFocus()
            binding.etPass.error = "Required"
            return false
        }

        return true

    }
}