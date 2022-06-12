package com.example.brandat.ui.fragments.registeration.register
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.brandat.R
import com.example.brandat.databinding.FragmentRegisterBinding
import com.example.brandat.models.Customer
import com.example.brandat.models.CustomerModel
import com.example.brandat.models.CustomerRegisterModel
import com.example.brandat.models.DefaultAddress
import com.example.brandat.utils.Constants.Companion.EMAIL_PATTERN
import com.example.brandat.utils.Constants.Companion.user
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()
    private lateinit var email: String
    private lateinit var name: String
    private lateinit var phone: String
    private lateinit var pass: String
    private lateinit var confirmPass: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_register,null)
        binding = FragmentRegisterBinding.bind(view)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        email = binding.emailLayout.editText?.text.toString()
        name = binding.firstNameEt.text.toString()
        pass = binding.passwordEt.text.toString()

        val address =
            DefaultAddress(address1 = "elmanshia", city = "alexandria", country = "egypt")



        binding.registerBtn.setOnClickListener {

            val customer = Customer(
                email = email,
                firstName = name,
                lastName = "ooo",
                state = "enabled",
                tags = pass,
                defaultAddress = address
            )
            val model = CustomerRegisterModel(customer)
            registerViewModel.registerCustomer(model)
            Log.d("TAG", "onViewCreated: email $email")

            //if( cheackEmpty()) {
              Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()


              registerViewModel.signUpSuccess.observe(viewLifecycleOwner) {
                  Log.e("TAG", "=======> :$it")
                  if (it != null) {
                      user = it.customer
                      Toast.makeText(requireContext(), "Succesully", Toast.LENGTH_SHORT).show()
                  } else {
                      Toast.makeText(requireContext(), "Try Again", Toast.LENGTH_SHORT).show()

               //   }
              }
          }


        }

    }

    private fun cheackEmpty(): Boolean {
        email = binding.emailEt.text.toString()
        name = binding.firstNameEt.text.toString()
        phone = binding.numberEt.text.toString()
        pass = binding.passwordEt.text.toString()
        confirmPass = binding.confirmEt.text.toString()
        if (name.isEmpty()) {
            binding.firstNameEt.requestFocus()
            binding.firstNameEt.error = "Required"
            return false
        }
        if (email.isEmpty()) {
            binding.emailEt.requestFocus()
            binding.emailEt.error = "Required"

            return false
        }
        if (!isValidEmail(email)) {
            binding.emailEt.error = "Not valid Email"
        }

        if (phone.isEmpty()) {
            binding.numberEt.requestFocus()
            binding.numberEt.error = "Required"
            return false
        }
        if (pass.isEmpty()) {
            binding.passwordEt.requestFocus()
            binding.passwordEt.error = "Required"
            return false
        }
        if (confirmPass.isEmpty()) {
            binding.confirmEt.requestFocus()
            binding.confirmEt.error = "Required"
            return false
        }
        if (pass != confirmPass) {
            binding.confirmEt.requestFocus()
            binding.confirmEt.error = "password doesn't match"
            return false
        }
        return true
    }

    fun isValidEmail(str: String): Boolean {
        return EMAIL_PATTERN.matcher(str).matches()
    }

}